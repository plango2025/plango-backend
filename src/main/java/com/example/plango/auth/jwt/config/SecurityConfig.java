package com.example.plango.auth.jwt.config;

import com.example.plango.auth.jwt.filter.JwtAuthenticationFilter;
import com.example.plango.auth.jwt.handler.CustomLogoutHandler;
import com.example.plango.auth.jwt.handler.CustomLogoutSuccessHandler;
import com.example.plango.auth.jwt.service.UserRefreshTokenService;
import com.example.plango.auth.jwt.service.impl.UserDetailsServiceImpl;
import com.example.plango.auth.jwt.service.TokenBlacklistService;
import com.example.plango.common.url.UrlManager;
import com.example.plango.auth.jwt.service.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRefreshTokenService userRefreshTokenService;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager jwtManager;
    private final UrlManager urlManager;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        // 정적 자원에 대한 요청 무시
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        String frontDomain=urlManager.getFrontDomain();

        // 접근 허용할 도메인, 메서드, 헤더 설정
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList(frontDomain));    // 프론트 서버 도메인
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);

        // 모든 경로에 대해서 API 호출 허용
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        // AuthenticationManager 설정
        AuthenticationManagerBuilder authManagerBuilder=http.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        AuthenticationManager authManager=authManagerBuilder.build();
        http.authenticationManager(authManager);

        // LogoutFilter 설정
        LogoutHandler logoutHandler = new CustomLogoutHandler(userRefreshTokenService, tokenBlacklistService, jwtManager);
        LogoutSuccessHandler logoutSuccessHandler = new CustomLogoutSuccessHandler(jwtManager);
        LogoutFilter logoutFilter = new LogoutFilter(logoutSuccessHandler, logoutHandler);
        logoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"));
        http.addFilterBefore(logoutFilter, LogoutFilter.class);

        // JwtAuthenticationFilter 설정
        JwtAuthenticationFilter jwtAuthenticationFilter=new JwtAuthenticationFilter(userDetailsService, tokenBlacklistService, jwtManager);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // CORS 처리
        http.cors((httpSecurityCorsConfigurer) -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()));

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        // 세션을 무상태로 설정
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

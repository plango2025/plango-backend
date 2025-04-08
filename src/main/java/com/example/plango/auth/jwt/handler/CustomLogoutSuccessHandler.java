package com.example.plango.auth.jwt.handler;

import com.example.plango.auth.jwt.service.JwtManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    private final JwtManager jwtManager;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 응답 구성
        response.setStatus(HttpServletResponse.SC_OK);

        // 발급했던 Refresh Token 쿠키 만료시키기
        response.addHeader(HttpHeaders.SET_COOKIE, jwtManager.generateClearRefreshCookie().toString());
    }
}
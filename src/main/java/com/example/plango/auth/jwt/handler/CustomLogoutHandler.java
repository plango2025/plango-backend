package com.example.plango.auth.jwt.handler;

import com.example.plango.auth.jwt.dto.JwtDTO;
import com.example.plango.auth.jwt.exception.TokenException;
import com.example.plango.auth.jwt.service.JwtManager;
import com.example.plango.auth.jwt.service.TokenBlacklistService;
import com.example.plango.auth.jwt.service.UserRefreshTokenService;
import com.example.plango.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final UserRefreshTokenService userRefreshTokenService;
    private final TokenBlacklistService tokenBlacklistService;
    private final JwtManager jwtManager;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 요청으로 들어온 Access 토큰, Refresh 토큰을 블랙리스트에 등록
        // 응답으로 만료된 Refresh 쿠키 전달

        try{
            // SecurityContext 비우기
            SecurityContextHolder.clearContext();

            // 토큰 블랙리스트 등록
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            // Access 토큰이 존재하고
            if (authorizationHeader != null) {
                // Bearer 토큰이라면
                if(authorizationHeader.startsWith("Bearer ")){
                    String accessToken = authorizationHeader.substring(7);

                    // Access 토큰이 유효하다면
                    if(jwtManager.validateToken(accessToken)){
                        // Access 토큰을 블랙리스트에 등록
                        tokenBlacklistService.blacklist(accessToken, "logout");
                    }
                }
            }

            // Refresh 토큰이 있다면
            String refreshToken= RequestUtil.getRefreshTokenFromRequest(request);
            if(refreshToken!=null){
                // 로그아웃 했으니 이전에 발급한 Refresh 토큰들을 사용할 수 없도록 방지
                JwtDTO jwtDTO=jwtManager.parseTokenToDTO(refreshToken);
                String userId=jwtDTO.getSubject();

                // DB에서 Refresh 토큰 제거
                userRefreshTokenService.deleteByUserId(userId);

                // Refresh 토큰이 유효하다면
                if(jwtManager.validateToken(refreshToken)){
                    // Refresh 토큰을 블랙리스트에 등록
                    tokenBlacklistService.blacklist(refreshToken, "logout");
                }
            }
        } catch (TokenException e){
            // 토큰 검증에서 예외가 발생한 경우 무시
            e.printStackTrace();
        }
    }
}


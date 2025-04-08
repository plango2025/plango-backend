package com.example.plango.auth.jwt.controller;

import com.example.plango.auth.jwt.dto.JwtDTO;
import com.example.plango.auth.jwt.exception.TokenException;
import com.example.plango.auth.jwt.model.UserRefreshToken;
import com.example.plango.auth.jwt.service.TokenBlacklistService;
import com.example.plango.auth.jwt.service.UserRefreshTokenService;
import com.example.plango.auth.jwt.service.JwtManager;
import com.example.plango.common.exception.ErrorCode;
import com.example.plango.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;


@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class JwtController {
    private final TokenBlacklistService tokenBlacklistService;
    private final UserRefreshTokenService userRefreshTokenService;
    private final JwtManager jwtManager;


    /**
     * 토큰 재발급 API
     * (Refresh 토큰을 이용하여 새로운 Access 토큰, Refresh 토큰 발급)
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // Refresh 토큰 추출
        String refreshToken= RequestUtil.getRefreshTokenFromRequest(request);

        // Refresh Token이 없으면 토큰 재발급 불가
        if(refreshToken==null){
            throw new TokenException(ErrorCode.TOKEN_UNACCEPT);
        }

        // Refresh 토큰이 유효하다면
        if(jwtManager.validateToken(refreshToken)){
            // 블랙 리스트에 등록되었는지 확인
            if(!tokenBlacklistService.exists(refreshToken)){
                // DB에서 관리 중인 refresh 토큰인지 검사 (유효한 토큰인지 검증)
                Optional<UserRefreshToken> userRefreshTokenOptional=userRefreshTokenService.readByRefreshToken(refreshToken);
                if(userRefreshTokenOptional.isPresent() && userRefreshTokenOptional.get().getRefreshToken().equals(refreshToken)){
                    // 응답 구성
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_OK);

                    // Access Token 재발급
                    JwtDTO jwtDTO=jwtManager.parseTokenToDTO(refreshToken);
                    String userId = jwtDTO.getSubject();
                    String accessToken = jwtManager.generateAccessToken(userId);
                    response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

                    // Refresh Token 재발급
                    ResponseCookie newRefreshTokenCookie= jwtManager.generateRefreshCookie(userId);
                    response.addHeader(HttpHeaders.SET_COOKIE, newRefreshTokenCookie.toString());

                    // DB에 Refresh Token 갱신
                    String newRefreshToken=newRefreshTokenCookie.getValue();
                    userRefreshTokenService.createOrUpdate(userId, newRefreshToken);
                }
                else{
                    // DB에서 관리 중인 토큰이 아니라면 예외 처리
                    throw new TokenException(ErrorCode.INVALID_TOKEN);
                }
            }
            else{
                // 토큰 블랙리스트에 등록된 토큰이라면 예외 처리
                throw new TokenException(ErrorCode.TOKEN_BLACKLISTED);
            }
        }
    }
}

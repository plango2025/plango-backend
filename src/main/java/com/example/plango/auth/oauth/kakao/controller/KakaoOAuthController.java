package com.example.plango.auth.oauth.kakao.controller;


import com.example.plango.auth.jwt.service.UserRefreshTokenService;
import com.example.plango.auth.oauth.service.OAuthService;
import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.common.url.UrlManager;
import com.example.plango.common.url.UrlPath;
import com.example.plango.auth.jwt.service.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth/kakao")
public class KakaoOAuthController {
    private final OAuthService oAuthService;
    private final UserRefreshTokenService userRefreshTokenService;
    private final UrlManager urlManager;
    private final JwtManager jwtManager;

    // 카카오 로그인 환경 변수
    @Value("${kakao.client.id}")
    private String kakaoClientId;


    /**
     * 카카오 로그인 API
     * (카카오 로그인 페이지 URL 전달)
     *
     * @return  (status) 200,
     *          (body) 카카오 로그인 URL
     */
    @GetMapping("/login")
    public ResponseEntity<SuccessResponse> redirectToKakaoAuth(){
        // 카카오 로그인 화면으로 리다이렉트
        String kakaoLoginUrl=urlManager.getUrl(UrlPath.KAKAO_LOGIN_URL);
        String kakaoLoginRedirectUri=urlManager.getUrl(UrlPath.KAKAO_LOGIN_REDIRECT_URI);
        String url = new StringBuilder(kakaoLoginUrl)
                .append("?client_id=").append(kakaoClientId)
                .append("&redirect_uri=").append(kakaoLoginRedirectUri)
                .append("&response_type=code")
                .toString();

        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(Map.of("redirectUrl", url)));
   }

    /**
     * 카카오 로그인 콜백 API
     * (인가 코드를 받아 로그인 처리를 완료시킴)
     *
     * @param code 인가 코드
     * @return  (status) 302,
     *          (header > Location) 성공한 경우 -> 프론트 메인 페이지 URL
     *          (header > Location) 실패한 경우 -> 프론트 500 에러 페이지 URL
     *          (header > Set-Cookie) Refresh 토큰
     */
    @GetMapping("/callback")
    public ResponseEntity<SuccessResponse> callback(@RequestParam String code){
        try{
            // 카카오 로그인
            String userId=oAuthService.loginWithKakao(code);
            // HTTP Header 설정
            HttpHeaders headers=new HttpHeaders();

            // Location 헤더 설정 (성공했으면, 메인 페이지로 리다이렉트)
            String frontMainPageUrl=urlManager.getUrl(UrlPath.FRONT_MAIN_PAGE);
            headers.add(HttpHeaders.LOCATION, frontMainPageUrl);

            // Refresh 토큰 발급
            ResponseCookie refreshTokenCookie= jwtManager.generateRefreshCookie(userId);
            headers.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

            // DB에 Refresh 토큰 갱신
            String refreshToken=refreshTokenCookie.getValue();
            userRefreshTokenService.createOrUpdate(userId, refreshToken);

            return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
        } catch (Exception e){
            // 실패 했으면, 500 에러 페이지로 리다이렉트

            HttpHeaders headers=new HttpHeaders();

            // Location 설정
            String front500ErrorPageUrl= urlManager.getUrl(UrlPath.FRONT_500_ERROR_PAGE);
            headers.add(HttpHeaders.LOCATION, front500ErrorPageUrl);

            return ResponseEntity.status(HttpStatus.FOUND)
                    .headers(headers)
                    .build();

        }
    }
}

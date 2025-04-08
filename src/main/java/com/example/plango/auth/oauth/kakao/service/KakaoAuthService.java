package com.example.plango.auth.oauth.kakao.service;

import com.example.plango.auth.oauth.kakao.dto.KakaoUserInfoReadResponseDTO;

public interface KakaoAuthService {
    String getAccessToken(String authCode);
    KakaoUserInfoReadResponseDTO getUserInfoByToken(String accessToken);
}

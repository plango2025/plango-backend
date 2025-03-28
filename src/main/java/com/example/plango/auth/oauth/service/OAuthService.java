package com.example.plango.auth.oauth.service;

import com.example.plango.user.dto.UserProfileReadResponseDTO;

public interface OAuthService {
    UserProfileReadResponseDTO loginWithKakao(String code);
}

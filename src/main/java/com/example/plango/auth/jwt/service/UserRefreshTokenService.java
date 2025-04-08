package com.example.plango.auth.jwt.service;

import com.example.plango.auth.jwt.model.UserRefreshToken;

import java.util.Optional;

public interface UserRefreshTokenService {
    Optional<UserRefreshToken> readByRefreshToken(String refreshToken);
    void createOrUpdate(String userId, String refreshToken);
    void deleteByUserId(String userId);
}

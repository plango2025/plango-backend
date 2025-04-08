package com.example.plango.auth.jwt.service;

public interface TokenBlacklistService {
    void blacklist(String token, String reason);
    boolean exists(String token);
}

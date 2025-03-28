package com.example.plango.auth.jwt.repository;

import com.example.plango.auth.jwt.model.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, String> {
    boolean existsByRefreshToken(String refreshToken);
    Optional<UserRefreshToken> findByRefreshToken(String refreshToken);
}

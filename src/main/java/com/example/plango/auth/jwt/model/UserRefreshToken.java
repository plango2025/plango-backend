package com.example.plango.auth.jwt.model;

import com.example.plango.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_refresh_token")
public class UserRefreshToken extends BaseEntity {
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    private String userId;  // 사용자 ID
    @Column(name = "refresh_token", nullable = false, updatable = true)
    private String refreshToken;    // Refresh 토큰
    @Column(name = "expiration", nullable = false, updatable = true)
    private Instant expiration; // 토큰 만료 시간
}

package com.example.plango.auth.jwt.service.impl;

import com.example.plango.auth.jwt.dto.JwtDTO;
import com.example.plango.auth.jwt.model.UserRefreshToken;
import com.example.plango.auth.jwt.repository.UserRefreshTokenRepository;
import com.example.plango.auth.jwt.service.JwtManager;
import com.example.plango.auth.jwt.service.UserRefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRefreshTokenServiceImpl implements UserRefreshTokenService {
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final JwtManager jwtManager;

    /**
     * DB에서 refresh 토큰 조회
     * @param refreshToken refresh 토큰
     * @return DB에서 조회한 refresh 토큰
     */
    @Override
    public Optional<UserRefreshToken> readByRefreshToken(String refreshToken){
        return userRefreshTokenRepository.findByRefreshToken(refreshToken);
    }

    /**
     * DB에 refresh 토큰 갱신
     * @param userId 사용자 ID
     * @param refreshToken refresh 토큰
     */
    @Override
    public void createOrUpdate(String userId, String refreshToken){
        // 토큰 만료기한 추출
        JwtDTO jwtDTO=jwtManager.parseTokenToDTO(refreshToken);
        Instant expiration=jwtDTO.getExpiration().toInstant();

        // UserRefreshToken 엔티티 생성
        UserRefreshToken userRefreshToken=UserRefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .expiration(expiration)
                .build();

        // DB에 refresh 토큰 갱신
        UserRefreshToken test=userRefreshTokenRepository.save(userRefreshToken);
        System.out.println("DB에 새로 저장된 토큰 : "+test);
    }

    /**
     * 특정 사용자의 refresh 토큰을 DB에서 제거
     * @param userId 사용자 ID
     */
    public void deleteByUserId(String userId){
       userRefreshTokenRepository.deleteById(userId);
    }
}

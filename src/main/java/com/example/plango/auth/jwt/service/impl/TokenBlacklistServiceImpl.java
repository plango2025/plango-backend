package com.example.plango.auth.jwt.service.impl;

import com.example.plango.auth.jwt.dto.JwtDTO;
import com.example.plango.auth.jwt.service.TokenBlacklistService;
import com.example.plango.infra.redis.model.RedisKeyType;
import com.example.plango.infra.redis.repository.RedisRepository;
import com.example.plango.auth.jwt.service.JwtManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
@Transactional
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {
    private final RedisRepository redisRepository;
    private final JwtManager jwtManager;

    @Override
    public void blacklist(String token, String reason){
        // 토큰 정보 추출
        JwtDTO jwtDTO=jwtManager.parseTokenToDTO(token);

        // 토큰의 만료 기한 계산
        Date expiration=jwtDTO.getExpiration();
        long expMS=expiration.getTime();
        long nowMS=System.currentTimeMillis();
        long remainingMS= expMS-nowMS;

        // 블랙 리스트에 토큰 등록 (TTL : 토큰 만료 시간)
        redisRepository.saveWithTTL(RedisKeyType.BLACKLIST, token, reason, remainingMS, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean exists(String token){
        return redisRepository.exists(RedisKeyType.BLACKLIST, token);
    }
}

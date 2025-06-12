package com.example.plango.like.service.impl;

import com.example.plango.common.enums.TargetType;
import com.example.plango.like.dto.LikeRequest;
import com.example.plango.like.dto.LikeResponse;
import com.example.plango.like.dto.LikeCountResponse;
import com.example.plango.like.exception.AlreadyLikedException;
import com.example.plango.common.exception.InvalidTargetTypeException;
import com.example.plango.like.exception.LikeCreationException;
import com.example.plango.like.model.Like;
import com.example.plango.like.repository.LikeRepository;
import com.example.plango.like.service.LikeService;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    @Override
    @Transactional
    public LikeResponse like(LikeRequest request, UserInfo user) {
        // 중복 체크
        likeRepository.findByUserAndTargetIdAndTargetType(user, request.getTargetId(), request.getTargetType())
                .ifPresent(like -> {
                    throw new AlreadyLikedException("이미 좋아요를 누른 대상입니다.");
                });

        try {
            Like saved = likeRepository.save(request.toEntity(user));
            return LikeResponse.fromEntity(saved);
        } catch (Exception e) {
            throw new LikeCreationException("좋아요 저장 중 오류가 발생했습니다.");
        }
    }

    @Override
    public void unlike(LikeRequest request, UserInfo user) {
        // 좋아요 조회
        Like like = likeRepository.findByUserAndTargetIdAndTargetType(user, request.getTargetId(), request.getTargetType())
                .orElseThrow(() -> new AlreadyLikedException("좋아요가 존재하지 않습니다."));

        // 좋아요 취소
        likeRepository.delete(like);
    }

    @Override
    public LikeCountResponse getLikeCount(Long targetId, String targetTypeStr) {
        try {
            TargetType targetType = TargetType.fromValue(targetTypeStr);
            Long count = likeRepository.countByTargetIdAndTargetType(targetId, targetType);
            return LikeCountResponse.of(targetId, targetType, count);
        } catch (IllegalArgumentException e) {
            throw new InvalidTargetTypeException("유효하지 않은 타겟 타입입니다.");
        }
    }
}

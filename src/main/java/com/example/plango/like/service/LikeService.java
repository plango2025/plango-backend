package com.example.plango.like.service;

import com.example.plango.like.dto.LikeRequest;
import com.example.plango.like.dto.LikeResponse;
import com.example.plango.like.dto.LikeCountResponse;
import com.example.plango.user.model.UserInfo;

public interface LikeService {

    /**
     * 좋아요 등록
     * - 동일 사용자 + 동일 대상 중복 좋아요 불가
     * - 좋아요 수 반환용 응답 포함
     */
    LikeResponse like(LikeRequest request, UserInfo user);

    /**
     * 특정 대상(target_id + target_type)에 대한 총 좋아요 수 반환
     */
    LikeCountResponse getLikeCount(Long targetId, String targetType);
}

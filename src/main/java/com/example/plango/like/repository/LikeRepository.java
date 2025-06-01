package com.example.plango.like.repository;

import com.example.plango.common.enums.TargetType;
import com.example.plango.like.model.Like;
import com.example.plango.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    /**
     * 특정 사용자, 대상 ID, 대상 타입에 대한 좋아요 존재 여부 확인
     */
    Optional<Like> findByUserAndTargetIdAndTargetType(UserInfo user, Long targetId, TargetType targetType);

    /**
     * 특정 대상(target_id + target_type)에 대한 좋아요 수 조회
     */
    Long countByTargetIdAndTargetType(Long targetId, TargetType targetType);
}

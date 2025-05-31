package com.example.plango.review.repository;

import com.example.plango.common.enums.TargetType;
import com.example.plango.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 대상 유형(SCHEDULE or PLACE)에 해당하는 모든 리뷰 조회
    List<Review> findByTargetTypeOrderByCreatedAtDesc(TargetType targetType);

    // 특정 단일 리뷰 조회 (기본 제공되는 findById 사용)
}

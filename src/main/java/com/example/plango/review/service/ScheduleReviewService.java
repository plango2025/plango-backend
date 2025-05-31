package com.example.plango.review.service;

import com.example.plango.review.dto.ScheduleReviewCreateRequest;
import com.example.plango.review.dto.ScheduleReviewResponse;
import com.example.plango.user.model.UserInfo;

import java.util.List;

public interface ScheduleReviewService {

    /**
     * ✍️ 일정 리뷰 작성
     * @param request 리뷰 작성 요청 DTO
     * @param user 작성자 정보
     * @return 작성된 리뷰 정보
     */
    ScheduleReviewResponse createReview(ScheduleReviewCreateRequest request, UserInfo user);

    /**
     * 📄 전체 리뷰 목록 조회
     * @return 모든 리뷰 리스트
     */
    List<ScheduleReviewResponse> getAllReviews();

    /**
     * 🔍 리뷰 단건 조회
     * @param id 리뷰 ID
     * @return 해당 리뷰 정보
     */
    ScheduleReviewResponse getReviewById(Long id);

    void deleteReview(Long reviewId, UserInfo user);
}

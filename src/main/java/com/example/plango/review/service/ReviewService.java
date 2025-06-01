package com.example.plango.review.service;

import com.example.plango.review.dto.ReviewCreateRequest;
import com.example.plango.review.dto.ReviewResponse;
import com.example.plango.review.dto.ReviewUpdateRequest;
import com.example.plango.user.model.UserInfo;

import java.util.List;

public interface ReviewService {

    /**
     * 리뷰 작성
     * @param request 리뷰 작성 요청 DTO
     * @param user 작성자 정보
     * @return 작성된 리뷰 정보
     */
    ReviewResponse createReview(ReviewCreateRequest request, UserInfo user);

    /**
     * 전체 리뷰 목록 조회 (특정 대상 유형에 한해)
     * @param targetType 대상 유형 (SCHEDULE or PLACE)
     * @return 리뷰 리스트
     */
    List<ReviewResponse> getReviewsByTargetType(String targetType); // ENUM 직접 받을 수도 있음

    /**
     * 리뷰 단건 조회
     * @param reviewId 리뷰 ID
     * @return 해당 리뷰 정보
     */
    ReviewResponse getReviewById(Long reviewId);

//    /**
//     * 리뷰 수정
//     * @param reviewId 리뷰 ID
//     * @param request 수정할 내용
//     * @param user 작성자 정보 (권한 확인용)
//     * @return 수정된 리뷰 정보
//     */
//    ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request, UserInfo user);

    /**
     * 리뷰 삭제
     * @param reviewId 리뷰 ID
     * @param user 요청자 정보 (권한 확인용)
     */
    void deleteReview(Long reviewId, UserInfo user);
}

package com.example.plango.user.controller;

import com.example.plango.common.dto.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.plango.review.dto.ReviewResponse;
import com.example.plango.review.service.ReviewService;

import org.springframework.http.HttpStatus;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}")
public class UserActivityController {
    private final ReviewService reviewService;

    /**
     * 특정 사용자가 작성한 리뷰 목록 조회 API
     *
     * @param userId 사용자 ID
     * @return 사용자 리뷰 목록 (200 OK)
     */
    @GetMapping("/schedule-reviews")
    public ResponseEntity<SuccessResponse> readReviews(@PathVariable String userId) {
        // 리뷰 목록 조회
        List<ReviewResponse> reviews = reviewService.getReviewsByUserId(userId);

        // 응답 생성
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(reviews));
    }
}

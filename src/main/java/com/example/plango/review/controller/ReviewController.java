package com.example.plango.review.controller;

import com.example.plango.common.security.SecurityService;
import com.example.plango.review.dto.ReviewCreateRequest;
import com.example.plango.review.dto.ReviewResponse;
import com.example.plango.review.dto.ReviewUpdateRequest;
import com.example.plango.review.service.ReviewService;
import com.example.plango.user.model.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final SecurityService securityService;

    /**
     * ✍ 리뷰 작성
     */
    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@RequestBody @Valid ReviewCreateRequest request) {
        UserInfo user = securityService.getUserInfo();
        ReviewResponse response = reviewService.createReview(request, user);
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 유형(SCHEDULE/PLACE)에 대한 전체 리뷰 목록 조회
     * @param targetType 필수 요청 파라미터
     */
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviewsByTargetType(@RequestParam String targetType) {
        List<ReviewResponse> responseList = reviewService.getReviewsByTargetType(targetType);
        return ResponseEntity.ok(responseList);
    }

    /**
     * 리뷰 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable Long id) {
        ReviewResponse response = reviewService.getReviewById(id);
        return ResponseEntity.ok(response);
    }

//    /**
//     * 리뷰 수정
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id,
//                                                       @RequestBody @Valid ReviewUpdateRequest request) {
//        UserInfo user = securityService.getUserInfo();
//        ReviewResponse response = reviewService.updateReview(id, request, user);
//        return ResponseEntity.ok(response);
//    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        UserInfo user = securityService.getUserInfo();
        reviewService.deleteReview(id, user);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}

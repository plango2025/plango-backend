package com.example.plango.review.controller;

import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.common.security.SecurityService;
import com.example.plango.review.dto.ReviewCreateRequest;
import com.example.plango.review.dto.ReviewResponse;
import com.example.plango.review.service.ReviewService;
import com.example.plango.user.model.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
     * 리뷰 작성 API
     *
     * @param request 리뷰 생성 요청 DTO
     * @return 생성된 리뷰 정보 (200 OK)
     */
    @PostMapping
    public ResponseEntity<SuccessResponse> createReview(@RequestBody @Valid ReviewCreateRequest request) {
        UserInfo user = securityService.getUserInfo();
        ReviewResponse response = reviewService.createReview(request, user);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(response));
    }

    /**
     * 특정 유형(SCHEDULE/PLACE)에 대한 전체 리뷰 목록 조회 API
     *
     * @param targetType 대상 타입 (SCHEDULE or PLACE)
     * @return 리뷰 리스트 (200 OK)
     */
    @GetMapping
    public ResponseEntity<SuccessResponse> getReviewsByTargetType(@RequestParam String targetType) {
        List<ReviewResponse> responses = reviewService.getReviewsByTargetType(targetType);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(responses));
    }

    /**
     * 리뷰 단건 조회 API
     *
     * @param id 리뷰 ID
     * @return 해당 리뷰 정보 (200 OK)
     */
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getReviewById(@PathVariable Long id) {
        ReviewResponse response = reviewService.getReviewById(id);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(response));
    }

//    /**
//     * 리뷰 수정 API
//     *
//     * @param id 리뷰 ID
//     * @param request 리뷰 수정 요청 DTO
//     * @return 수정된 리뷰 정보 (200 OK)
//     */
//    @PutMapping("/{id}")
//    public ResponseEntity<SuccessResponse> updateReview(@PathVariable Long id,
//                                                        @RequestBody @Valid ReviewUpdateRequest request) {
//        UserInfo user = securityService.getUserInfo();
//        ReviewResponse response = reviewService.updateReview(id, request, user);
//        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(response));
//    }

    /**
     * 리뷰 삭제 API
     *
     * @param id 리뷰 ID
     * @return 성공 응답 (204 No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        UserInfo user = securityService.getUserInfo();
        reviewService.deleteReview(id, user);
        return ResponseEntity.noContent().build();
    }
}

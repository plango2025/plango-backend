package com.example.plango.review.controller;

import com.example.plango.common.security.SecurityService;
import com.example.plango.review.dto.ScheduleReviewCreateRequest;
import com.example.plango.review.dto.ScheduleReviewResponse;
import com.example.plango.review.service.ScheduleReviewService;
import com.example.plango.user.model.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ScheduleReviewController {

    private final ScheduleReviewService scheduleReviewService;
    private final SecurityService securityService;

    /**
     * 리뷰 작성
     */
    @PostMapping
    public ResponseEntity<ScheduleReviewResponse> createReview(
            @RequestBody @Valid ScheduleReviewCreateRequest request
    ) {
        UserInfo user = securityService.getUserInfo();
        ScheduleReviewResponse response = scheduleReviewService.createReview(request, user);
        return ResponseEntity.ok(response);
    }

    /**
     * 전체 리뷰 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<ScheduleReviewResponse>> getAllReviews() {
        List<ScheduleReviewResponse> responseList = scheduleReviewService.getAllReviews();
        return ResponseEntity.ok(responseList);
    }

    /**
     * 특정 리뷰 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleReviewResponse> getReviewById(@PathVariable Long id) {
        ScheduleReviewResponse response = scheduleReviewService.getReviewById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        UserInfo user = securityService.getUserInfo();
        scheduleReviewService.deleteReview(id, user);
        return ResponseEntity.noContent().build(); // 204 No Content
    }



}

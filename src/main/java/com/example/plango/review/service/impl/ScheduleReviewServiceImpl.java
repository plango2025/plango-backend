package com.example.plango.review.service.impl;

import com.example.plango.review.dto.ScheduleReviewCreateRequest;
import com.example.plango.review.dto.ScheduleReviewResponse;
import com.example.plango.review.model.ScheduleReview;
import com.example.plango.review.repository.ScheduleReviewRepository;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import com.example.plango.review.service.ScheduleReviewService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleReviewServiceImpl implements ScheduleReviewService {

    private final ScheduleReviewRepository scheduleReviewRepository;

    /**
     * ✍일정 리뷰 작성
     */
    @Override
    @Transactional
    public ScheduleReviewResponse createReview(ScheduleReviewCreateRequest request, UserInfo user) {

        ScheduleReview saved = scheduleReviewRepository.save(request.toEntity(user));
        return ScheduleReviewResponse.fromEntity(saved);
    }

    /**
     * 전체 리뷰 목록 조회
     */
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleReviewResponse> getAllReviews() {
        return scheduleReviewRepository.findAll().stream()
                .map(ScheduleReviewResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 리뷰 단건 조회
     */
    @Override
    @Transactional(readOnly = true)
    public ScheduleReviewResponse getReviewById(Long id) {
        ScheduleReview review = scheduleReviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
        return ScheduleReviewResponse.fromEntity(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, UserInfo user) {
        ScheduleReview review = scheduleReviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));

        // 작성자 본인만 삭제 가능
        if (!review.getUser().getId().equals(user.getId())) {
            throw new SecurityException("리뷰를 삭제할 권한이 없습니다.");
        }

        scheduleReviewRepository.delete(review);
    }



}

package com.example.plango.review.service.impl;

import com.example.plango.common.enums.TargetType;
import com.example.plango.review.dto.ReviewCreateRequest;
import com.example.plango.review.dto.ReviewResponse;
import com.example.plango.review.dto.ReviewUpdateRequest;
import com.example.plango.review.exception.*;
import com.example.plango.review.model.Review;
import com.example.plango.review.repository.ReviewRepository;
import com.example.plango.review.service.ReviewService;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.plango.common.exception.InvalidTargetTypeException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(ReviewCreateRequest request, UserInfo user) {
        try {
            Review saved = reviewRepository.save(request.toEntity(user));
            return ReviewResponse.fromEntity(saved);
        } catch (Exception e) {
            throw new ReviewCreationException("리뷰 생성 중 오류가 발생했습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByTargetType(String targetTypeStr) {
        try {
            TargetType targetType = TargetType.fromValue(targetTypeStr);
            return reviewRepository.findByTargetTypeOrderByCreatedAtDesc(targetType)
                    .stream()
                    .map(ReviewResponse::fromEntity)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new InvalidTargetTypeException("유효하지 않은 타겟 타입입니다.");
        } catch (Exception e) {
            throw new ReviewReadException("리뷰 목록 조회 중 오류가 발생했습니다.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));
        return ReviewResponse.fromEntity(review);
    }

    //    /**
//     * 🛠 리뷰 수정
//     */
//    @Override
//    @Transactional
//    public ReviewResponse updateReview(Long reviewId, ReviewUpdateRequest request, UserInfo user) {
//        Review review = reviewRepository.findById(reviewId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
//
//        validateAuthor(review, user);
//
//        review.update(
//                request.getTitle(),
//                request.getContent(),
//                request.getRating(),
//                request.getImages()
//        );
//
//        return ReviewResponse.fromEntity(review);
//    }

    @Override
    @Transactional
    public void deleteReview(Long reviewId, UserInfo user) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        validateAuthor(review, user);
        reviewRepository.delete(review);
    }

    private void validateAuthor(Review review, UserInfo user) {
        if (!review.getUser().getId().equals(user.getId())) {
            throw new ReviewPermissionDeniedException("리뷰에 대한 권한이 없습니다.");
        }
    }
}




package com.example.plango.review.dto;

import com.example.plango.common.enums.TargetType;
import com.example.plango.review.model.Review;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {

    @JsonProperty("review_id")
    private Long id;

    @JsonProperty("target_id")
    private String targetId;

    @JsonProperty("target_type")
    private TargetType targetType;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonProperty("rating")
    private Float rating;

    @JsonProperty("images")
    private List<String> images;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static ReviewResponse fromEntity(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .targetId(review.getTargetId())
                .targetType(review.getTargetType())
                .userId(review.getUser().getId())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .images(review.getImages())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}

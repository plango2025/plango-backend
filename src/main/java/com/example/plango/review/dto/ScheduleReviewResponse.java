package com.example.plango.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.plango.review.model.ScheduleReview;
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
public class ScheduleReviewResponse {

    @JsonProperty("review_id")
    private Long id;

    @JsonProperty("schedule_id")
    private Long scheduleId;

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

    public static ScheduleReviewResponse fromEntity(ScheduleReview review) {
        return ScheduleReviewResponse.builder()
                .id(review.getId())
                .scheduleId(review.getScheduleId())
                .userId(review.getUserId())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .images(review.getImages())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}


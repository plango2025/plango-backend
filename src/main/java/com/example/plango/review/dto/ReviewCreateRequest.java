package com.example.plango.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.plango.review.model.ScheduleReview;
import com.example.plango.user.model.UserInfo;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleReviewCreateRequest {

    @NotNull(message = "일정 ID는 필수입니다.")
    @JsonProperty("schedule_id")
    private String scheduleId;

    @NotBlank(message = "리뷰 제목을 입력해주세요.")
    @Size(max = 100, message = "제목은 최대 100자까지 입력할 수 있습니다.")
    @JsonProperty("title")
    private String title;

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    @JsonProperty("content")
    private String content;

    @NotNull(message = "평점을 입력해주세요.")
    @DecimalMin(value = "0.5", message = "최소 평점은 0.5입니다.")
    @DecimalMax(value = "5.0", message = "최대 평점은 5.0입니다.")
    @JsonProperty("rating")
    private Float rating;

    @JsonProperty("images")
    private List<@NotBlank(message = "이미지 URL은 비어 있을 수 없습니다.") String> images;

    public ScheduleReview toEntity(UserInfo user) {
        return ScheduleReview.builder()
                .scheduleId(this.scheduleId)
                .user(user)
                .title(this.title)
                .content(this.content)
                .rating(this.rating)
                .images(this.images)
                .build();
    }
}

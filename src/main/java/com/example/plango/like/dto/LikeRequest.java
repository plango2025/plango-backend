package com.example.plango.like.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.plango.common.enums.TargetType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeRequest {

    @NotNull(message = "좋아요 대상 ID는 필수입니다.")
    @JsonProperty("target_id")
    private Long targetId;

    @NotNull(message = "좋아요 대상 타입은 필수입니다.")
    @JsonProperty("target_type")
    private TargetType targetType; // ENUM: SCHEDULE_REVIEW, PLACE_REVIEW, COMMENT

    public Like toEntity(Long userId) {
        return Like.builder()
                .targetId(this.targetId)
                .targetType(this.targetType)
                .userId(userId)
                .build();
    }

}


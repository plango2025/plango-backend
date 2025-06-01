package com.example.plango.like.dto;

import com.example.plango.common.enums.TargetType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeCountResponse {

    @JsonProperty("target_id")
    private Long targetId;

    @JsonProperty("target_type")
    private TargetType targetType;

    @JsonProperty("like_count")
    private Long likeCount;

    public static LikeCountResponse of(Long targetId, TargetType targetType, Long likeCount) {
        return LikeCountResponse.builder()
                .targetId(targetId)
                .targetType(targetType)
                .likeCount(likeCount)
                .build();
    }
}

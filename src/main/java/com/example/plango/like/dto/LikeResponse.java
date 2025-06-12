package com.example.plango.like.dto;

import com.example.plango.common.enums.TargetType;
import com.example.plango.like.model.Like;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeResponse {

    @JsonProperty("like_id")
    private Long id;

    @JsonProperty("target_id")
    private Long targetId;

    @JsonProperty("target_type")
    private TargetType targetType;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static LikeResponse fromEntity(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .targetId(like.getTargetId())
                .targetType(like.getTargetType())
                .userId(like.getUser().getId())
                .createdAt(like.getRegDate())
                .build();
    }
}

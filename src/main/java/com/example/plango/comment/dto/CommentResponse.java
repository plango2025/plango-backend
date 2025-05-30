package com.example.plango.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.plango.comment.model.Comment;
import com.example.plango.common.enums.TargetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    @JsonProperty("comment_id")
    private Long id;

    @JsonProperty("target_id")
    private Long targetId;

    @JsonProperty("target_type")
    private TargetType targetType; // SCHEDULE_REVIEW or PLACE_REVIEW

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static CommentResponse fromEntity(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .targetId(comment.getTargetId())
                .targetType(comment.getTargetType())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .build();
    }
}

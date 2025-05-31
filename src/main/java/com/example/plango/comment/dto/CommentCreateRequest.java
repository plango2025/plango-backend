package com.example.plango.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.plango.comment.model.Comment;
import com.example.plango.common.enums.TargetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.example.plango.user.model.UserInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreateRequest {

    @NotNull(message = "댓글이 작성될 리뷰 ID는 필수입니다.")
    @JsonProperty("target_id")
    private Long targetId;

    @NotNull(message = "댓글 대상 타입은 필수입니다.")
    @JsonProperty("target_type")
    private TargetType targetType; // Enum: SCHEDULE_REVIEW, PLACE_REVIEW

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @JsonProperty("content")
    private String content;

    public Comment toEntity(UserInfo user) {
        return Comment.builder()
                .targetId(this.targetId)
                .targetType(this.targetType)
                .user(user)
                .content(this.content)
                .build();
    }
}

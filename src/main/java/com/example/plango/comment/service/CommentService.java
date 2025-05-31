package com.example.plango.comment.service;

import com.example.plango.comment.dto.CommentCreateRequest;
import com.example.plango.comment.dto.CommentResponse;
import com.example.plango.comment.dto.CommentUpdateRequest;
import com.example.plango.common.enums.TargetType;
import com.example.plango.user.model.UserInfo;

import java.util.List;

public interface CommentService {

    // 댓글 등록
    CommentResponse createComment(CommentCreateRequest request, UserInfo user);

    // 댓글 전체 조회 (특정 리뷰에 대한)
    List<CommentResponse> getCommentsByTarget(Long targetId, TargetType targetType);

    // 댓글 수정
    CommentResponse updateComment(Long commentId, CommentUpdateRequest request, UserInfo user);

    // 댓글 삭제
    void deleteComment(Long commentId, UserInfo user);
}

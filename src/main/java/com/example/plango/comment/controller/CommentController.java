package com.example.plango.comment.controller;

import com.example.plango.comment.dto.CommentCreateRequest;
import com.example.plango.comment.dto.CommentResponse;
import com.example.plango.comment.dto.CommentUpdateRequest;
import com.example.plango.comment.service.CommentService;
import com.example.plango.common.enums.TargetType;
import com.example.plango.common.security.SecurityService;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final SecurityService securityService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentCreateRequest request) {
        UserInfo user = securityService.getUserInfo();
        CommentResponse response = commentService.createComment(request, user);
        return ResponseEntity.ok(response);
    }

    // 특정 대상(SCHEDULE_REVIEW / PLACE_REVIEW)에 대한 댓글 전체 조회
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@RequestParam Long targetId,
                                                             @RequestParam TargetType targetType) {
        List<CommentResponse> responses = commentService.getCommentsByTarget(targetId, targetType);
        return ResponseEntity.ok(responses);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        UserInfo user = securityService.getUserInfo();
        commentService.deleteComment(commentId, user);
        return ResponseEntity.noContent().build();
    }
}

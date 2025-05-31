package com.example.plango.comment.controller;

import com.example.plango.comment.dto.CommentCreateRequest;
import com.example.plango.comment.dto.CommentResponse;
import com.example.plango.comment.dto.CommentUpdateRequest;
import com.example.plango.comment.service.CommentService;
import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.common.enums.TargetType;
import com.example.plango.common.security.SecurityService;
import com.example.plango.user.model.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final SecurityService securityService;

    /**
     * 댓글 등록 API
     *
     * @param request 댓글 생성 요청
     * @return 생성된 댓글 정보 (200 OK)
     */
    @PostMapping
    public ResponseEntity<SuccessResponse> createComment(@RequestBody @Valid CommentCreateRequest request) {
        UserInfo user = securityService.getUserInfo();
        CommentResponse response = commentService.createComment(request, user);
        return ResponseEntity.ok(SuccessResponse.of(response));
    }

    /**
     * 특정 대상(SCHEDULE_REVIEW / PLACE_REVIEW)에 대한 댓글 전체 조회 API
     *
     * @param targetId   대상 ID
     * @param targetType 대상 타입 (SCHEDULE_REVIEW / PLACE_REVIEW)
     * @return 댓글 리스트 (200 OK)
     */
    @GetMapping
    public ResponseEntity<SuccessResponse> getComments(@RequestParam Long targetId,
                                                       @RequestParam TargetType targetType) {
        List<CommentResponse> responses = commentService.getCommentsByTarget(targetId, targetType);
        return ResponseEntity.ok(SuccessResponse.of(responses));
    }

    /**
     * 댓글 삭제 API
     *
     * @param commentId 댓글 ID
     * @return 성공 응답 (204 No Content)
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        UserInfo user = securityService.getUserInfo();
        commentService.deleteComment(commentId, user);
        return ResponseEntity.noContent().build();
    }
}


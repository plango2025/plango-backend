package com.example.plango.comment.service.impl;

import com.example.plango.comment.dto.CommentCreateRequest;
import com.example.plango.comment.dto.CommentResponse;
import com.example.plango.comment.dto.CommentUpdateRequest;
import com.example.plango.comment.model.Comment;
import com.example.plango.comment.repository.CommentRepository;
import com.example.plango.comment.service.CommentService;
import com.example.plango.common.enums.TargetType;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponse createComment(CommentCreateRequest request, UserInfo user) {
        Comment comment = request.toEntity(user);
        Comment saved = commentRepository.save(comment);
        return CommentResponse.fromEntity(saved);
    }

    @Override
    public List<CommentResponse> getCommentsByTarget(Long targetId, TargetType targetType) {
        return commentRepository.findByTargetIdAndTargetTypeOrderByCreatedAtAsc(targetId, targetType)
                .stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, UserInfo user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        validateAuthor(comment, user);
        commentRepository.delete(comment);
    }

    private void validateAuthor(Comment comment, UserInfo user) {
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("댓글에 대한 권한이 없습니다.");
        }
    }
}

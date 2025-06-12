package com.example.plango.comment.service.impl;

import com.example.plango.comment.dto.CommentCreateRequest;
import com.example.plango.comment.dto.CommentResponse;
import com.example.plango.comment.exception.CommentCreationException;
import com.example.plango.comment.exception.CommentNotFoundException;
import com.example.plango.comment.exception.CommentPermissionDeniedException;
import com.example.plango.comment.exception.CommentReadException;
import com.example.plango.comment.model.Comment;
import com.example.plango.comment.repository.CommentRepository;
import com.example.plango.comment.service.CommentService;
import com.example.plango.common.enums.TargetType;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        try {
            Comment comment = request.toEntity(user);
            Comment saved = commentRepository.save(comment);
            return CommentResponse.fromEntity(saved);
        } catch (Exception e) {
            throw new CommentCreationException("댓글 생성 중 오류가 발생했습니다.");
        }
    }

    @Override
    public List<CommentResponse> getCommentsByTarget(Long targetId, TargetType targetType) {
        try {
            return commentRepository.findByTargetIdAndTargetTypeOrderByIdAsc(targetId, targetType)
                    .stream()
                    .map(CommentResponse::fromEntity)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CommentReadException("댓글 조회 중 오류가 발생했습니다.");
        }
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, UserInfo user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("댓글이 존재하지 않습니다."));

        validateAuthor(comment, user);
        commentRepository.delete(comment);
    }

    private void validateAuthor(Comment comment, UserInfo user) {
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentPermissionDeniedException("댓글에 대한 권한이 없습니다.");
        }
    }
}

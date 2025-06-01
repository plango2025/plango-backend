package com.example.plango.comment.repository;


import com.example.plango.comment.model.Comment;
import com.example.plango.common.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 리뷰(targetId + targetType)에 대한 모든 댓글 조회
    List<Comment> findByTargetIdAndTargetTypeOrderByCreatedAtAsc(Long targetId, TargetType targetType);

}

package com.example.plango.comment.model;

import com.example.plango.common.enums.TargetType;
import com.example.plango.common.model.BaseEntity;
import com.example.plango.user.model.UserInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 대상 식별자 (리뷰 ID)
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    // 댓글 대상 타입 (SCHEDULE_REVIEW or PLACE_REVIEW)
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 30)
    private TargetType targetType;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    // 댓글 본문
    @Lob
    @Column(nullable = false)
    private String content;

    @Builder
    public Comment(Long targetId, TargetType targetType, UserInfo user, String content) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.user = user;
        this.content = content;
    }

}

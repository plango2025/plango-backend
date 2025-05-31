package com.example.plango.like.model;

import com.example.plango.common.enums.TargetType;
import com.example.plango.common.model.BaseEntity;
import com.example.plango.user.model.UserInfo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "likes",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_like",
                columnNames = {"user_id", "target_id", "target_type"}
        )
)
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 좋아요 대상 ID (리뷰 ID 또는 댓글 ID)
    @Column(name = "target_id", nullable = false)
    private Long targetId;

    // 대상 타입: SCHEDULE_REVIEW / PLACE_REVIEW / COMMENT
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 30)
    private TargetType targetType;

    // 좋아요 누른 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Builder
    public Like(Long targetId, TargetType targetType, UserInfo user) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.user = user;
    }
}

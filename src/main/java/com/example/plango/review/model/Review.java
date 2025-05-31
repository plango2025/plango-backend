package com.example.plango.review.model;

import com.example.plango.common.enums.TargetType;
import com.example.plango.common.model.BaseEntity;
import com.example.plango.user.model.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일정 ID 또는 장소 ID (MongoDB ObjectId 또는 장소 PK)
    @Column(name = "target_id", nullable = false, length = 50)
    private String targetId;

    // 대상 타입 (SCHEDULE / PLACE)
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 30)
    private TargetType targetType;

    // 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    // 별점 (1.0 ~ 5.0)
    @Column(nullable = false)
    private Float rating;

    // 제목
    @Column(nullable = false, length = 100)
    private String title;

    // 텍스트 후기
    @Lob
    @Column(nullable = false)
    private String content;

    // 이미지 URL 리스트 (JSON 배열 형태로 저장됨)
    @ElementCollection
    @CollectionTable(name = "review_images", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    @Builder
    public Review(String targetId, TargetType targetType, UserInfo user, Float rating, String title, String content, List<String> images) {
        this.targetId = targetId;
        this.targetType = targetType;
        this.user = user;
        this.rating = rating;
        this.title = title;
        this.content = content;
        if (images != null) {
            this.images = images;
        }
    }
}

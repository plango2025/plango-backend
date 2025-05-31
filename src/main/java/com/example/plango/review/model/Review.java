package com.example.plango.review.model;

import com.example.plango.common.model.BaseEntity;
import com.example.plango.user.model.UserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedule_review")
public class ScheduleReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MongoDB 일정 ID (ObjectId 문자열)
    @Column(name = "schedule_id", nullable = false, length = 50)
    private String scheduleId;

    // 작성자 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    // 별점
    @Column(nullable = false)
    private Float rating;

    // 후기 내용
    @Lob
    @Column(nullable = false)
    private String content;

    // 후기 제목
    @Column(nullable = false, length = 100)
    private String title;

    // 이미지 URL 리스트 → JSON 배열 형태로 저장
    @ElementCollection
    @CollectionTable(name = "schedule_review_images", joinColumns = @JoinColumn(name = "review_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    @Builder
    public ScheduleReview(String scheduleId, UserInfo user, Float rating, String content, String title, List<String> images) {
        this.scheduleId = scheduleId;
        this.user = user;
        this.rating = rating;
        this.content = content;
        this.title = title;
        if (images != null) {
            this.images = images;
        }
    }
}

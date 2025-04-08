package com.example.plango.auth.oauth.model;

import com.example.plango.common.model.BaseEntity;
import com.example.plango.user.model.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SocialUserAccountKey.class)
@Table(name = "social_user_account")
public class SocialUserAccount extends BaseEntity {
    @Id
    @Column(name = "social_type", nullable = false)
    private String socialType;
    @Id
    @Column(name = "social_id", nullable = false)
    private String socialId;  // OAuth 제공 업체의 유저 ID
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_info_id", nullable = false, updatable = false)
    private UserInfo userInfo;
}

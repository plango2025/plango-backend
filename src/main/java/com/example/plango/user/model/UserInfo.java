package com.example.plango.user.model;

import com.example.plango.common.model.BaseEntity;
import com.example.plango.file.model.StorageFile;
import com.example.plango.user.service.UserInfoService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
public class UserInfo extends BaseEntity {
    @Id
    @Column(name = "id", length= UserInfoService.USER_ID_LENGTH, nullable = false, updatable = false)
    private String id;
    @Column(name = "nickname", length= UserInfoService.MAX_NICKNAME_LENGTH, nullable = false, updatable = true)
    private String nickname;
    @OneToOne(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "profile_image_id", nullable = true, updatable = true)
    private StorageFile profileImage;
    @Column(name = "address", nullable = true, updatable = true)
    private String address;
    @Column(name = "birth", nullable = true, updatable = true)
    private LocalDate birth;
    @Column(name = "about", nullable = true, updatable = true)
    private String about;
}

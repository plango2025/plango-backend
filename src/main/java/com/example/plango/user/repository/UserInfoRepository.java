package com.example.plango.user.repository;

import com.example.plango.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    boolean existsById(String id);
}

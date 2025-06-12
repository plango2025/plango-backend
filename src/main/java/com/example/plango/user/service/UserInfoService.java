package com.example.plango.user.service;

import com.example.plango.user.dto.UserInfoReadResponseDTO;
import com.example.plango.user.dto.UserInfoUpdateRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService {
    int USER_ID_LENGTH=15;
    int MAX_NICKNAME_LENGTH=20;

    UserInfoReadResponseDTO readMe();
    String update(String userId, UserInfoUpdateRequestDTO userInfoUpdateRequestDTO);
    String updateProfileImage(String userId, MultipartFile file) throws Exception;
}

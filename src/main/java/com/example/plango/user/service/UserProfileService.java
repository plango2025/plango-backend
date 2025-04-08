package com.example.plango.user.service;

import com.example.plango.user.dto.UserProfileReadResponseDTO;

public interface UserProfileService {
    UserProfileReadResponseDTO readProfileById(String userId);
    UserProfileReadResponseDTO readMyProfile();
}

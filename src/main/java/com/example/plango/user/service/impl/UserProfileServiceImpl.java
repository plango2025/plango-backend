package com.example.plango.user.service.impl;

import com.example.plango.common.security.SecurityService;
import com.example.plango.user.dto.UserProfileReadResponseDTO;
import com.example.plango.user.model.UserInfo;
import com.example.plango.user.repository.UserInfoRepository;
import com.example.plango.user.service.UserProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserInfoRepository userInfoRepository;
    private final SecurityService securityService;


    /**
     * 사용자 프로필 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 프로필
     */
    @Override
    public UserProfileReadResponseDTO readProfileById(String userId){
        // 엔티티 조회
        UserInfo userInfo=userInfoRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException("존재하지 않는 사용자입니다. (id = "+userId+")"));

        return UserProfileReadResponseDTO.builder()
                .id(userInfo.getId())
                .nickname(userInfo.getNickname())
                .build();
    }

    /**
     * 나의 프로필 조회
     * [필요 권한 : 로그인 상태]
     *
     * @return 나의 프로필 정보
     */
    @Override
    public UserProfileReadResponseDTO readMyProfile(){
        UserInfo myUserInfo= securityService.getUserInfo();
        return UserProfileReadResponseDTO.builder()
                .id(myUserInfo.getId())
                .nickname(myUserInfo.getNickname())
                .build();
    }
}

package com.example.plango.user.controller;

import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.user.dto.UserProfileReadResponseDTO;
import com.example.plango.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;


    /**
     * 나의 프로필 조회 API
     * @return  (status) 200
     *          (body)  조회 성공 메세지,
     *                  나의 프로필
     */
    @GetMapping("/me")
    public ResponseEntity<SuccessResponse> readMyProfile(){
        // 나의 프로필 조회
        UserProfileReadResponseDTO userProfileDTO=userProfileService.readMyProfile();

        // 응답 생성
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(userProfileDTO));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponse> readUserProfile(@PathVariable String userId){
        // 프로필 조회
        UserProfileReadResponseDTO userProfileDTO=userProfileService.readProfileById(userId);

        // 응답 생성
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(userProfileDTO));
    }
}

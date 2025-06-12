package com.example.plango.user.controller;

import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.user.dto.UserInfoReadResponseDTO;
import com.example.plango.user.dto.UserInfoUpdateRequestDTO;
import com.example.plango.user.service.UserInfoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("/me")
    public ResponseEntity<SuccessResponse> readMyUserInfo() {
        // 나의 유저 정보 조회
        UserInfoReadResponseDTO userInfoDTO = userInfoService.readMe();

        // 응답 생성
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(userInfoDTO));
    }

    /**
     * 사용자 정보 수정 API
     * @param userId 사용자 ID
     * @param userInfoUpdateRequestDTO 사용자 정보 수정 요청
     * @return  (status) 200,
     *          (body) 사용자 ID
     */
    @PatchMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> updateUserInfo(@PathVariable(name = "userId") String userId,
                                                              @Valid @RequestBody UserInfoUpdateRequestDTO userInfoUpdateRequestDTO) {
        // 사용자 정보 수정
        String result = userInfoService.update(userId, userInfoUpdateRequestDTO);

        // 응답 생성
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(result));
    }

    /**
     * 사용자 프로필 사진 설정 API
     * @param profileImage 프로필 사진
     * @return 사진이 설정된 사용자의 ID
     */
    @PutMapping(value = "/{userId}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse> updateProfileImage(@PathVariable(name = "userId") String userId,
                                                                  @RequestParam(value = "profileImage") MultipartFile profileImage) throws Exception{
        // 사용자 프로필 사진 설정
        String profileImageUrl=userInfoService.updateProfileImage(userId, profileImage);

        // 응답 생성
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(profileImageUrl));
    }
}
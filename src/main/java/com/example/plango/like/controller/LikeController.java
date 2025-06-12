package com.example.plango.like.controller;

import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.common.security.SecurityService;
import com.example.plango.like.dto.LikeRequest;
import com.example.plango.like.dto.LikeResponse;
import com.example.plango.like.dto.LikeCountResponse;
import com.example.plango.like.service.LikeService;
import com.example.plango.user.model.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;
    private final SecurityService securityService;

    /**
     * 좋아요 등록 API
     *
     * @param request 좋아요 대상 정보
     * @return 좋아요 상태 응답 (200 OK)
     */
    @PostMapping
    public ResponseEntity<SuccessResponse> like(@RequestBody @Valid LikeRequest request) {
        UserInfo user = securityService.getUserInfo();
        LikeResponse response = likeService.like(request, user);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(response));
    }

    @DeleteMapping("")
    public ResponseEntity<SuccessResponse> unlike(@RequestBody @Valid LikeRequest request) {
        UserInfo user = securityService.getUserInfo();
        likeService.unlike(request, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    
    /**
     * 좋아요 수 조회 API
     *
     * @param targetId   대상 ID
     * @param targetType 대상 타입 (예: SCHEDULE, PLACE 등)
     * @return 좋아요 수 응답 (200 OK)
     */
    @GetMapping("/count")
    public ResponseEntity<SuccessResponse> getLikeCount(@RequestParam("target_id") Long targetId,
                                                        @RequestParam("target_type") String targetType) {
        LikeCountResponse response = likeService.getLikeCount(targetId, targetType);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(response));
    }
}

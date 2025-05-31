package com.example.plango.like.controller;

import com.example.plango.common.security.SecurityService;
import com.example.plango.like.dto.LikeRequest;
import com.example.plango.like.dto.LikeResponse;
import com.example.plango.like.dto.LikeCountResponse;
import com.example.plango.like.service.LikeService;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;
    private final SecurityService securityService;

    /**
     * 좋아요 등록
     */
    @PostMapping
    public ResponseEntity<LikeResponse> like(@RequestBody LikeRequest request) {
        UserInfo user = securityService.getUserInfo();
        LikeResponse response = likeService.like(request, user);
        return ResponseEntity.ok(response);
    }

    /**
     * 좋아요 수 조회
     */
    @GetMapping("/count")
    public ResponseEntity<LikeCountResponse> getLikeCount(@RequestParam("target_id") Long targetId,
                                                          @RequestParam("target_type") String targetType) {
        LikeCountResponse response = likeService.getLikeCount(targetId, targetType);
        return ResponseEntity.ok(response);
    }
}

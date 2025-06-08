package com.example.plango.auth.oauth.service.impl;

import com.example.plango.auth.oauth.kakao.dto.KakaoUserInfoReadResponseDTO;
import com.example.plango.auth.oauth.kakao.service.KakaoAuthService;
import com.example.plango.auth.oauth.model.SocialType;
import com.example.plango.auth.oauth.model.SocialUserAccount;
import com.example.plango.auth.oauth.model.SocialUserAccountKey;
import com.example.plango.auth.oauth.repository.SocialUserAccountRepository;
import com.example.plango.auth.oauth.service.OAuthService;
import com.example.plango.user.model.UserInfo;
import com.example.plango.user.repository.UserInfoRepository;
import com.example.plango.user.service.UserInfoService;
import com.example.plango.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
    private final SocialUserAccountRepository socialUserAccountRepository;
    private final UserInfoRepository userInfoRepository;
    private final KakaoAuthService kakaoAuthService;


    @Override
    public String loginWithKakao(String code){
        // 카카오 Access Token 발급
        String kakaoAccessToken= kakaoAuthService.getAccessToken(code);

        // 카카오 사용자 정보 조회하기
        KakaoUserInfoReadResponseDTO kakaoUserInfoDTO=kakaoAuthService.getUserInfoByToken(kakaoAccessToken);
        if(kakaoUserInfoDTO==null){
            throw new OAuth2AuthenticationException("Kakao 사용자 정보 조회에 실패했습니다.");
        }

        String kakaoUserId=kakaoUserInfoDTO.getId().toString();
        String kakaoNickname=kakaoUserInfoDTO.getProperties().getNickname();
        if(kakaoUserId == null || kakaoNickname == null){
            throw new OAuth2AuthenticationException("Kakao 사용자 정보를 조회할 수 없습니다.");
        }
        // 이미 회원가입한 유저인지 조회
        SocialUserAccountKey socialUserAccountKey= SocialUserAccountKey.builder()
                .socialType(SocialType.KAKAO.name())
                .socialId(kakaoUserId)
                .build();
        Optional<SocialUserAccount> optionalSocialUserAccount=socialUserAccountRepository.findById(socialUserAccountKey);

        String userId;
        if(optionalSocialUserAccount.isPresent()){
            // 이미 존재하는 사용자이면
            userId=optionalSocialUserAccount.get().getUserInfo().getId();
        }
        else{
            // 존재하지 않는 사용자이면, 자동 회원가입
            userId=createSocialUser(SocialType.KAKAO.name(), kakaoUserId, kakaoNickname);
        }

        return userId;
    }

    private String createSocialUser(String socialType, String socialUserId, String socialNickname){
        // 중복되지 않는 사용자 ID 생성
        String userId;
        do {
            userId= StringUtil.generateRandomString(StringUtil.USER_ID_CHARACTERS, UserInfoService.USER_ID_LENGTH);
        }while(userInfoRepository.existsById(userId));

        // UserInfo 엔티티 생성 (닉네임, 프로필 사진은 자동 설정)
        UserInfo userInfo=UserInfo.builder()
                .id(userId)
                .nickname(socialNickname)
                .build();

        // SocialUserAccount 엔티티 생성
        SocialUserAccount socialUserAccount= SocialUserAccount.builder()
                .socialType(socialType)
                .socialId(socialUserId)
                .userInfo(userInfo)
                .build();

        // DB 저장
        socialUserAccountRepository.save(socialUserAccount);

        // 등록된 사용자 ID 반환
        return userId;
    }
}

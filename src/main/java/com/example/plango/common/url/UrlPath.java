package com.example.plango.common.url;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UrlPath {
    FRONT_MAIN_PAGE(UrlSource.FRONT, "/"),
    FRONT_500_ERROR_PAGE(UrlSource.FRONT, "/error/500"),
    PYTHON_AI_SCHEDULE(UrlSource.EXTERNAL, "http://localhost:8000/api/schedule/ai"),
    KAKAO_LOGIN_REDIRECT_URI(UrlSource.BACK, "/api/oauth/kakao/callback"),
    KAKAO_LOGIN_URL(UrlSource.EXTERNAL, "https://kauth.kakao.com/oauth/authorize"),
    KAKAO_TOKEN_URL(UrlSource.EXTERNAL, "https://kauth.kakao.com/oauth/token"),
    KAKAO_USER_INFO_URL(UrlSource.EXTERNAL, "https://kapi.kakao.com/v2/user/me");


    private final UrlSource source;
    private final String path;
}

package com.example.plango.common.url;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UrlPath {
    FRONT_MAIN_PAGE(UrlSource.FRONT, "/"),
    FRONT_500_ERROR_PAGE(UrlSource.FRONT, "/error/500"),
    KAKAO_LOGIN_REDIRECT_URI(UrlSource.BACK, "/api/oauth/kakao/callback"),
    PYTHON_AI_SCHEDULE_URL(UrlSource.PYTHON, "/api/schedules/ai"),
    KAKAO_LOGIN_URL(UrlSource.EXTERNAL, "https://kauth.kakao.com/oauth/authorize"),
    KAKAO_TOKEN_URL(UrlSource.EXTERNAL, "https://kauth.kakao.com/oauth/token"),
    KAKAO_USER_INFO_URL(UrlSource.EXTERNAL, "https://kapi.kakao.com/v2/user/me"),
    KAKAO_LOCATION_URL(UrlSource.EXTERNAL, "https://dapi.kakao.com/v2/local/search/keyword.json"),;


    private final UrlSource source;
    private final String path;
}

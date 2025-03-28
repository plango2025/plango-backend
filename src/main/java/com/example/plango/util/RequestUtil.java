package com.example.plango.util;

import com.example.plango.auth.jwt.service.JwtManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class RequestUtil {
    /**
     * 쿠키 배열에서 특정 쿠키를 찾아서 값 조회
     * @param cookies 쿠키 배열
     * @param cookieName 쿠키 이름
     * @return 쿠키 값
     */
    public static String getValueFromCookies(Cookie[] cookies, String cookieName){
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    /**
     * HttpServletRequest에서 Refresh Token 추출
     * @param request HttpServletRequest
     * @return Refresh Token
     */
    public static String getRefreshTokenFromRequest(HttpServletRequest request){
        return getValueFromCookies(request.getCookies(), JwtManager.REFRESH_TOKEN_COOKIE);
    }
}

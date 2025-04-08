package com.example.plango.common.security.impl;

import com.example.plango.auth.jwt.exception.TokenException;
import com.example.plango.auth.jwt.model.UserDetailsImpl;
import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.security.SecurityService;
import com.example.plango.user.model.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class SecurityServiceImpl implements SecurityService {
    /**
     * 인증된 사용자의 ID를 추출
     * @return 사용자 ID
     * @throws TokenException 인증되지 않은 경우
     */
    @Override
    public UserInfo getUserInfo() throws TokenException{
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null){
            Object principal=authentication.getPrincipal();
            if(principal instanceof UserDetailsImpl){
                return ((UserDetailsImpl)principal).getUserInfo();
            }
        }

        throw new TokenException(ErrorCode.TOKEN_UNACCEPT);
    }
}

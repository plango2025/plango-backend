package com.example.plango.auth.oauth.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OAuthExceptionHandler {
    // OAuth 인증 에러
    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleOAuth2AuthenticationException(OAuth2AuthenticationException e){
        ErrorCode errorCode=ErrorCode.OAUTH_FAILED;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode, e.getMessage()));
    }
}

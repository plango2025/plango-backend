package com.example.plango.auth.jwt.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.plango.common.exception.ErrorResponse;

@RestControllerAdvice
public class JwtExceptionHandler {
    // 토큰 예외 처리
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException e){
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }
}

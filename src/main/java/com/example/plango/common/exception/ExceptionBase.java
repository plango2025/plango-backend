package com.example.plango.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ExceptionBase extends RuntimeException {
    // ExceptionHandler에게 예외에 대한 정보를 전달하기 위한 클래스

    protected String errorCode;
    protected String errorMessage;
    protected HttpStatus status;

    public ExceptionBase(String errorCode, String errorMessage, HttpStatus status){
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
        this.status=status;
    }

    // ErrorCode 정보를 그대로 쓰는 경우
    public ExceptionBase(ErrorCode errorCode){
        this(errorCode.getCode(), errorCode.getMessage(),errorCode.getStatus());
    }
}

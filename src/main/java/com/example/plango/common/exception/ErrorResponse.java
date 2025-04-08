package com.example.plango.common.exception;

import com.example.plango.util.JsonUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class ErrorResponse extends HashMap<String, Object> {
    // 예외에 대한 정보를 받아서 Response Body를 구성하는 클래스
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    private ErrorResponse(String errorCode, String errorMessage) {
        this.put("errorCode", errorCode);
        this.put("errorMessage", errorMessage);
        this.put("timeStamp", LocalDateTime.now().format(formatter));
    }

    public static ErrorResponse of(ExceptionBase exception) {
        return new ErrorResponse(exception.getErrorCode(), exception.getErrorMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static ErrorResponse of(ErrorCode errorCode, String customMessage) {
        return new ErrorResponse(errorCode.getCode(), customMessage);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }
}

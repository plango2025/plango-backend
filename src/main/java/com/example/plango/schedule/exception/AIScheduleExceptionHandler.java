package com.example.plango.schedule.exception;

import com.example.plango.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AIScheduleExceptionHandler {
    // AI 일정 생성 예외 처리
    @ExceptionHandler(AIScheduleGenerationException.class)
    public ResponseEntity<ErrorResponse> handleAIScheduleGenerationException(AIScheduleGenerationException e){
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }
}

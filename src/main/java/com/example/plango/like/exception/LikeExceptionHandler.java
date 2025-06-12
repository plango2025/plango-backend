package com.example.plango.like.exception;

import com.example.plango.common.exception.InvalidTargetTypeException;
import com.example.plango.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LikeExceptionHandler {

    @ExceptionHandler(AlreadyLikedException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyLiked(AlreadyLikedException e) {
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(LikeCreationException.class)
    public ResponseEntity<ErrorResponse> handleCreation(LikeCreationException e) {
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }
}

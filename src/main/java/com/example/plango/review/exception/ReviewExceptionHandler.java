package com.example.plango.review.exception;

import com.example.plango.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReviewExceptionHandler {

    @ExceptionHandler(ReviewCreationException.class)
    public ResponseEntity<ErrorResponse> handleCreate(ReviewCreationException e) {
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(ReviewReadException.class)
    public ResponseEntity<ErrorResponse> handleRead(ReviewReadException e) {
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ReviewNotFoundException e) {
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }

    @ExceptionHandler(ReviewPermissionDeniedException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ReviewPermissionDeniedException e) {
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }
}

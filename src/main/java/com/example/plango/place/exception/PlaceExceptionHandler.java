package com.example.plango.place.exception;

import com.example.plango.common.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlaceExceptionHandler {

    @ExceptionHandler(TourStreamingException.class)
    public ResponseEntity<ErrorResponse> handleTourStreaming(TourStreamingException e) {
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }
}

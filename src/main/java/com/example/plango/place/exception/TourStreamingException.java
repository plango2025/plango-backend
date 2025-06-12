package com.example.plango.place.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class TourStreamingException extends ExceptionBase {
    public TourStreamingException(String message, Throwable cause) {
        super(ErrorCode.TOUR_STREAMING_FAILED);
        this.errorMessage = message;
        this.initCause(cause);
    }
}

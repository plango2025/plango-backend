package com.example.plango.review.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class ReviewReadException extends ExceptionBase {
    public ReviewReadException(String message) {
        super(ErrorCode.REVIEW_READ_FAILED);
        this.errorMessage = message;
    }
}

package com.example.plango.review.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class ReviewNotFoundException extends ExceptionBase {
    public ReviewNotFoundException(String message) {
        super(ErrorCode.REVIEW_NOT_FOUND);
        this.errorMessage = message;
    }
}

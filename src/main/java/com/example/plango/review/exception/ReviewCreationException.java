package com.example.plango.review.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class ReviewCreationException extends ExceptionBase {
    public ReviewCreationException(String message) {
        super(ErrorCode.REVIEW_CREATION_FAILED);
        this.errorMessage = message;
    }
}

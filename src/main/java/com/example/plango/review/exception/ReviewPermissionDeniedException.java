package com.example.plango.review.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class ReviewPermissionDeniedException extends ExceptionBase {
    public ReviewPermissionDeniedException(String message) {
        super(ErrorCode.REVIEW_FORBIDDEN);
        this.errorMessage = message;
    }
}

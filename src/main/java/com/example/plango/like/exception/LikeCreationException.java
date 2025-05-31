package com.example.plango.like.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class LikeCreationException extends ExceptionBase {
    public LikeCreationException(String message) {
        super(ErrorCode.LIKE_CREATION_FAILED);
        this.errorMessage = message;
    }
}

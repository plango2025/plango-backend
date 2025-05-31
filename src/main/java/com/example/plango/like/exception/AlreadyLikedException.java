package com.example.plango.like.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class AlreadyLikedException extends ExceptionBase {
    public AlreadyLikedException(String message) {
        super(ErrorCode.ALREADY_LIKED);
        this.errorMessage = message;
    }
}

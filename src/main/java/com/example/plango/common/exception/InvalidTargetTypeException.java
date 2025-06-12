package com.example.plango.common.exception;

public class InvalidTargetTypeException extends ExceptionBase {
    public InvalidTargetTypeException(String message) {
        super(ErrorCode.INVALID_TARGET_TYPE);
        this.errorMessage = message;
    }
}

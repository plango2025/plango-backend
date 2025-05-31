package com.example.plango.common.enums;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class InvalidTargetTypeException extends ExceptionBase {
    public InvalidTargetTypeException(String message) {
        super(ErrorCode.INVALID_TARGET_TYPE);
        this.errorMessage = message;
    }
}

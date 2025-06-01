package com.example.plango.schedule.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class AIScheduleGenerationException extends ExceptionBase {
    public AIScheduleGenerationException(ErrorCode errorCode, String message){
        super(errorCode);
        this.errorMessage=message;
    }
}

package com.example.plango.comment.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class CommentReadException extends ExceptionBase {
    public CommentReadException(String message) {
        super(ErrorCode.COMMENT_READ_FAILED);
        this.errorMessage = message;
    }
}

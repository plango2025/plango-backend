package com.example.plango.comment.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class CommentCreationException extends ExceptionBase {
    public CommentCreationException(String message) {
        super(ErrorCode.COMMENT_CREATION_FAILED);
        this.errorMessage = message;
    }
}

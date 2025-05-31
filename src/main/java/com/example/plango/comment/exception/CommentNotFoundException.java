package com.example.plango.comment.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class CommentNotFoundException extends ExceptionBase {
    public CommentNotFoundException(String message) {
        super(ErrorCode.COMMENT_NOT_FOUND);
        this.errorMessage = message;
    }
}

package com.example.plango.comment.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ExceptionBase;

public class CommentPermissionDeniedException extends ExceptionBase {
    public CommentPermissionDeniedException(String message) {
        super(ErrorCode.COMMENT_FORBIDDEN);
        this.errorMessage = message;
    }
}

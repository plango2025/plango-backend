package com.example.plango.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Validation 도중 발생한 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        final List<Map<String, String>> errors=new LinkedList<>();

        // 예외 메시지 가공
        BindingResult bindingResult=e.getBindingResult();
        bindingResult.getFieldErrors().forEach((fieldError)->{
            errors.add(Map.of(fieldError.getField(),fieldError.getDefaultMessage()));
        });

        ErrorCode errorCode=ErrorCode.INVALID_INPUT_VALUE;
        ErrorResponse errorResponse=ErrorResponse.of(errorCode);
        errorResponse.put("errors", errors);

        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    // 유효성 검사에서 제약 사항을 위반했을 때 발생하는 예외 처리
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e){
        final List<Map<String, String>> errors=new LinkedList<>();

        Set<ConstraintViolation<?>> violations=e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.add(Map.of(fieldName, errorMessage));
        }

        ErrorCode errorCode=ErrorCode.CONSTRAINT_VIOLATION;
        ErrorResponse errorResponse=ErrorResponse.of(errorCode);
        errorResponse.put("errors", errors);

        return ResponseEntity.status(errorCode.getStatus()).body(errorResponse);
    }

    // 잘못된 인자가 들어왔을 때 발생하는 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e){
        ErrorCode errorCode=ErrorCode.ILLEGAL_ARGUMENT;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // 필요한 요청 정보가 들어오지 않았을 때 발생하는 예외 처리
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestPartException(MissingServletRequestPartException e){
        ErrorCode errorCode=ErrorCode.MISSING_REQUEST_PART;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // 접근 거부 예외 처리
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e){
        ErrorCode errorCode=ErrorCode.ACCESS_DENIED;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // 존재하지 않는 URL로 요청한 경우 발생하는 예외 처리
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException e){
        ErrorCode errorCode=ErrorCode.NOT_FOUND_HANDLER;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // DB에 필요한 엔티티가 존재하지 않는 경우 발생하는 예외 처리
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e){
        ErrorCode errorCode=ErrorCode.ENTITY_NOT_FOUND;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // 사용자를 찾을 수 없는 경우 발생하는 예외
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
        ErrorCode errorCode=ErrorCode.USERNAME_NOT_FOUND;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // 데이터 무결성 위반 예외 처리
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        ErrorCode errorCode=ErrorCode.DATA_INTEGRITY_VIOLATION;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // 데이터 접근 예외 처리
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e){
        ErrorCode errorCode=ErrorCode.DATA_ACCESS_ERROR;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // 나머지 모든 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception e){
        e.printStackTrace();

        ErrorCode errorCode=ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    // TargetType 타입 에러
    @ExceptionHandler(InvalidTargetTypeException.class)
    public ResponseEntity<ErrorResponse> handleInvalidType(InvalidTargetTypeException e) {
        return ResponseEntity.status(e.getStatus()).body(ErrorResponse.of(e));
    }
}

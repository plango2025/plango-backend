package com.example.plango.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 예외에 대한 정보를 담고 있는 열거형

    // 시스템 에러
    INTERNAL_SERVER_ERROR("SYS001", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 인증, 권한 에러
    ACCESS_DENIED("AUT001", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    USERNAME_NOT_FOUND("AUT002", "해당 사용자를 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED),

    // 토큰 에러
    TOKEN_UNACCEPT("TKN001", "토큰을 받지 못했습니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_MALFORM("TKN002", "잘못된 형식의 토큰입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_INVALID_TYPE("TKN003", "토큰의 타입이 적절하지 않습니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_EXPIRED("TKN004", "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_BAD_SIGNATURE("TKN005", "토큰 시그니처가 잘못됐습니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_UNSUPPORTED("TKN006", "지원하지 않는 토큰입니다.", HttpStatus.UNAUTHORIZED),
    TOKEN_BLACKLISTED("TKN007", "블랙리스트에 등록된 토큰입니다.", HttpStatus.FORBIDDEN),
    INVALID_TOKEN("TKN008", "유효하지 않은 토큰입니다.", HttpStatus.UNAUTHORIZED),

    // OAuth 에러
    OAUTH_FAILED("OAU001", "OAuth 인증에 실패했습니다.", HttpStatus.UNAUTHORIZED),

    // 유효성 검증 에러
    INVALID_INPUT_VALUE("VAL001", "입력값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    CONSTRAINT_VIOLATION("VAL002", "요청 파라미터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    ILLEGAL_ARGUMENT("VAL003", "올바르지 않은 인자가 전달되었습니다.", HttpStatus.BAD_REQUEST),

    // 요청 에러
    MISSING_REQUEST_PART("REQ001", "요청에 필요한 데이터가 누락되었습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_HANDLER("REQ002", "요청한 URL을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // DB 에러
    ENTITY_NOT_FOUND("DB001", "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    DATA_INTEGRITY_VIOLATION("DB002", "데이터 무결성 제약 조건에 위배되었습니다.", HttpStatus.CONFLICT),
    DATA_ACCESS_ERROR("DB003", "데이터베이스 처리 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // AI 에러
    PYTHON_AI_ERROR("AI001", "AI 일정 생성 중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY),

    // 댓글 에러
    COMMENT_CREATION_FAILED("COM001", "댓글 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    COMMENT_READ_FAILED("COM002", "댓글 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    COMMENT_NOT_FOUND("COM003", "댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    COMMENT_FORBIDDEN("COM004", "댓글에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 좋아요 관련 에러
    ALREADY_LIKED("LIKE001", "이미 좋아요를 누른 대상입니다.", HttpStatus.CONFLICT),
    LIKE_CREATION_FAILED("LIKE002", "좋아요 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TARGET_TYPE("LIKE003", "유효하지 않은 대상 타입입니다.", HttpStatus.BAD_REQUEST),

    // 리뷰 관련 에러
    REVIEW_CREATION_FAILED("REV001", "리뷰 생성에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    REVIEW_READ_FAILED("REV002", "리뷰 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    REVIEW_NOT_FOUND("REV003", "리뷰를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    REVIEW_FORBIDDEN("REV004", "리뷰에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 장소 소개 관련 에러
    TOUR_STREAMING_FAILED("PLACE001", "장소 정보를 불러오는 중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY);


    private final String code;
    private final String message;
    private final HttpStatus status;
}

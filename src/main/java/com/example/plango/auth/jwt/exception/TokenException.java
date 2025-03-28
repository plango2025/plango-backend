package com.example.plango.auth.jwt.exception;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.exception.ErrorResponse;
import com.example.plango.common.exception.ExceptionBase;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;

import java.io.IOException;

public class TokenException extends ExceptionBase {
    public TokenException(ErrorCode errorCode){
        super(errorCode);
    }

    /**
     * TokenException은 Spring Security의 Filter단에서 많이 발생하는 JWT 인증 예외이기 때문에,
     * Controller Advice를 통해 응답을 처리할 수 없다.
     * 그래서 TokenException 내부에 응답을 처리하는 메서드가 필요하다.
     * @param response HTTP 서블릿 응답 객체
     */
    public void sendErrorResponse(HttpServletResponse response){
        this.printStackTrace();

        // 상태 코드 설정
        response.setStatus(status.value());

        // 응답 헤더 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 응답 바디 설정
        ErrorResponse errorResponse=ErrorResponse.of(this);

        try{
            // HTTP 응답 본문 작성
            response.getWriter().write(errorResponse.toJson());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}

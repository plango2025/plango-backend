package com.example.plango.auth.jwt.service;

import com.example.plango.auth.jwt.dto.JwtDTO;
import com.example.plango.auth.jwt.dto.TokenType;
import com.example.plango.auth.jwt.exception.TokenException;
import com.example.plango.common.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtManager {
    public static final String REFRESH_TOKEN_COOKIE="refresh_token";
    public static final String CLAIM_TOKEN_TYPE="token_type";

    private final int ACCESS_TOKEN_EXPIRATION_SEC=60*30;  // 30분
    private final int REFRESH_TOKEN_EXPIRATION_SEC=60*60*24*7;  // 7일
    @Value("${applesquare.jwt.secret}")
    private String jwtSecret;


    /**
     * JWT 서명을 위한 HMAC SHA 키를 생성합니다.
     *
     * 이 메서드는 제공된 Base64로 인코딩된 비밀 키(jwtSecret)를 디코딩하여
     * HMAC SHA 키를 생성합니다. 이 키는 JSON Web Token(JWT)의 서명에 사용됩니다.
     *
     * @return HMAC SHA 서명을 위한 Key 객체
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * JWT 생성
     * @param subject 주체
     * @param expirationSecond 유효 시간 (second)
     * @return JWT 문자열
     */
    private String generateToken(String subject, int expirationSecond, String tokenType) {
        return Jwts.builder()
                .setSubject(subject)
                .claim(CLAIM_TOKEN_TYPE, tokenType)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusSeconds(expirationSecond).toInstant()))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Access Token 생성
     * @param userId 사용자 ID
     * @return Access Token
     */
    public String generateAccessToken(String userId){
        return generateToken(userId, ACCESS_TOKEN_EXPIRATION_SEC, TokenType.ACCESS.name());
    }

    /**
     * Refresh Token을 담은 Http Only 쿠키 생성
     * @param userId 사용자 ID
     * @return Refresh Token을 담은 Http Only 쿠키
     */
    public ResponseCookie generateRefreshCookie(String userId){
        String refreshToken = generateToken(userId, REFRESH_TOKEN_EXPIRATION_SEC, TokenType.REFRESH.name());
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE, refreshToken)
                .path("/")
                .maxAge(REFRESH_TOKEN_EXPIRATION_SEC)
                .httpOnly(true)
                .build();
    }

    /**
     * 만료 기한이 지난 Refresh Token 생성
     * @return Refresh Token
     */
    public ResponseCookie generateClearRefreshCookie(){
        return ResponseCookie.from(REFRESH_TOKEN_COOKIE, "clear_token")
                .path("/")
                .maxAge(0)
                .httpOnly(true)
                .build();
    }

    /**
     * JWT 검증
     * @param token 토큰
     * @return 토큰 유효 여부
     * @throws TokenException TokenException
     */
    public boolean validateToken(String token) throws TokenException {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new TokenException(ErrorCode.TOKEN_MALFORM);
        } catch (ExpiredJwtException e) {
            throw new TokenException(ErrorCode.TOKEN_EXPIRED);
        } catch(SignatureException signatureException){
            throw new TokenException(ErrorCode.TOKEN_BAD_SIGNATURE);
        } catch (UnsupportedJwtException e) {
            throw new TokenException(ErrorCode.TOKEN_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new TokenException(ErrorCode.TOKEN_UNACCEPT);
        }
    }

    /**
     * JWT 문자열의 내용을 DTO로 변환
     * @param token JWT 문자열
     * @return JwtDTO
     */
    public JwtDTO parseTokenToDTO(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String tokenTypeStr=claims.get(CLAIM_TOKEN_TYPE, String.class);
        TokenType tokenType=null;
        if(tokenTypeStr!=null){
            tokenType=TokenType.valueOf(tokenTypeStr);
        }

        return JwtDTO.builder()
                .subject(claims.getSubject())
                .issuedAt(claims.getIssuedAt())
                .expiration(claims.getExpiration())
                .tokenType(tokenType)
                .build();
    }
}

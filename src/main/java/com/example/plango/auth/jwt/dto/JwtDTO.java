package com.example.plango.auth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtDTO {
    private String subject; // sub
    private Date issuedAt;  // iat
    private Date expiration;    // exp
    private TokenType tokenType;    // token_type
}

package com.example.plango.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileReadResponseDTO {
    private String id;
    private String nickname;
}

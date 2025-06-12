package com.example.plango.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoUpdateRequestDTO {
    private String nickname;
    private String address;
    private LocalDate birth;
    private String about;
}

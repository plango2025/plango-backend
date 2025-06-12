package com.example.plango.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoReadResponseDTO {
    private String id;
    private String nickname;
    private String profileImage;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private long tripCount;
    private String about;
}

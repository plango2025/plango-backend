package com.example.plango.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleFeedbackRequestDTO {
    @JsonProperty("feedback")
    @NotNull(message = "피드백을 입력해주세요.")
    @NotEmpty(message = "피드백을 입력해주세요.")
    private String feedback;
}

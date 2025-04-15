package com.example.plango.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReadResponseDTO {
    @JsonProperty("title")
    private String title;
    @JsonProperty("days")
    private List<DaySchedule> days;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DaySchedule {
        @JsonProperty("day")
        private int day;
        @JsonProperty("schedules")
        private List<ScheduleDetail> schedules;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDetail {
        @JsonProperty("order")
        private int order;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("image")
        @URL(message = "유효한 URL 형식이어야 합니다.")
        private String image;
        @JsonProperty("latitude")
        private Double latitude;  // 위도
        @JsonProperty("longitude")
        private Double longitude;  // 경도
    }
}
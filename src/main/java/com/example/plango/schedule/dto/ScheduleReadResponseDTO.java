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

    @JsonProperty("schedule_id")
    private String scheduleId;

    @JsonProperty("schedule")
    private Schedule schedule;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Schedule {
        @JsonProperty("title")
        private String title;

        @JsonProperty("days")
        private List<DaySchedule> days;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DaySchedule {
        @JsonProperty("day")
        private Integer day;

        @JsonProperty("places")
        private List<PlaceItem> places;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceItem {
        @JsonProperty("order")
        private Integer order;

        @JsonProperty("name")
        private String name;

        @JsonProperty("description")
        private String description;

        @JsonProperty("image")
        @URL(message = "유효한 URL 형식이어야 합니다.")
        private String image;

        @JsonProperty("latitude")
        private Double latitude;

        @JsonProperty("longitude")
        private Double longitude;
    }
}

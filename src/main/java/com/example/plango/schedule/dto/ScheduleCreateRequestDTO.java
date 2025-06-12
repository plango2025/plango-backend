package com.example.plango.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateRequestDTO {
    @JsonProperty("required_places")
    private List<Place> requiredPlaces;
    @JsonProperty("destination")
    private String destination;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("companion")
    private String companion;
    @JsonProperty("style")
    private String style;
    @Min(0)
    @JsonProperty("schedule_count")
    private Integer scheduleCount;
    @Min(0)
    @JsonProperty("budget")
    private Integer budget;
    @JsonProperty("extra")
    private String extra;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Place {
        @JsonProperty("name")
        private String name;
        @JsonProperty("address")
        private String address;
    }
}
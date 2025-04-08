package com.example.plango.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateRequestDTO {
    private String required_place;
    private String destination;
    private String duration;
    private String companion;
    private String travel_style;
    private int schedule_count;
    private int budget;
    private String extra_notes;
}

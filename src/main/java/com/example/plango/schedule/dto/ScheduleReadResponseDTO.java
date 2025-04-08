package com.example.plango.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleReadResponseDTO {

    private String title;

    private List<DaySchedule> days;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DaySchedule {

        private int day;

        private List<ScheduleDetail> schedules;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDetail {

        private int order;
        private String name;
        private String description;
        private String image;
    }
}

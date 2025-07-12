package com.example.plango.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleThumbnailDTO {
    @JsonProperty("schedule_id")
    private String scheduleId;
    private String title;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    private String destination;
    private String duration;
}
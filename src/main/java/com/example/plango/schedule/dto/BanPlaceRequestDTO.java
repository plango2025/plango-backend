package com.example.plango.schedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BanPlaceRequestDTO {
    @JsonProperty("places")
    private List<String> places;
}

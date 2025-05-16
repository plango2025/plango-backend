package com.example.plango.schedule.service;

import com.example.plango.schedule.dto.*;

public interface ScheduleService {
    ScheduleReadResponseDTO createSchedule(ScheduleCreateRequestDTO scheduleCreateRequestDTO);
    void pinPlaces(String scheduleId, PinPlaceRequestDTO pinPlaceRequestDTO);
    void banPlaces(String scheduleId, BanPlaceRequestDTO banPlaceRequestDTO);
    void keep(String scheduleId);
    ScheduleReadResponseDTO feedback(String scheduleId, ScheduleFeedbackRequestDTO feedbackRequestDTO);
    ScheduleReadResponseDTO recreate(String scheduleId);
}

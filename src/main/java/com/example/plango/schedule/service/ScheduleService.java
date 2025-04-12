package com.example.plango.schedule.service;

import com.example.plango.schedule.dto.ScheduleCreateRequestDTO;
import com.example.plango.schedule.dto.ScheduleReadResponseDTO;

public interface ScheduleService {
    ScheduleReadResponseDTO createAISchedule(ScheduleCreateRequestDTO scheduleCreateRequestDTO);
}

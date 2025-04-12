package com.example.plango.schedule.controller;

import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.schedule.dto.ScheduleCreateRequestDTO;
import com.example.plango.schedule.dto.ScheduleReadResponseDTO;
import com.example.plango.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    /**
     * AI 일정 생성 API
     * @param scheduleCreateRequestDTO 일정 생성 파라미터
     * @return  (status) 200,
     *          (body) 자동으로 생성된 일정 정보
     */
    @PostMapping(value = "/ai", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> createAISchedule(@RequestBody @Valid ScheduleCreateRequestDTO scheduleCreateRequestDTO){
        // AI 일정 생성
        ScheduleReadResponseDTO scheduleDTO= scheduleService.createAISchedule(scheduleCreateRequestDTO);

        // 응답
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(scheduleDTO));
    }
}

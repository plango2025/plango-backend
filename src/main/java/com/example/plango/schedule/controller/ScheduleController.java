package com.example.plango.schedule.controller;

import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.schedule.dto.*;
import com.example.plango.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;


    @PostMapping(value="/dummy-create")
    public ResponseEntity<SuccessResponse> createDummySchedule(){
        // 테스트 요청 생성
        ScheduleCreateRequestDTO scheduleCreateRequestDTO=ScheduleCreateRequestDTO.builder()
                .requiredPlaces(List.of())
                .destination("춘천")
                .duration(2)
                .companion("연인과")
                .style("힐링 여행")
                .scheduleCount(4)
                .budget(15)
                .extra("꽃 보고 싶어")
                .build();

        // AI 일정 생성
        ScheduleReadResponseDTO scheduleDTO= scheduleService.createSchedule(scheduleCreateRequestDTO);

        // 생성된 일정 보관
        scheduleService.keep(scheduleDTO.getScheduleId());

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(scheduleDTO));
    }
    /**
     * AI 일정 생성 API
     * @param scheduleCreateRequestDTO 일정 생성 파라미터
     * @return  (status) 200,
     *          (body) 자동으로 생성된 일정 정보
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> createSchedule(@RequestBody @Valid ScheduleCreateRequestDTO scheduleCreateRequestDTO){
        // AI 일정 생성
        ScheduleReadResponseDTO scheduleDTO= scheduleService.createSchedule(scheduleCreateRequestDTO);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.of(scheduleDTO));
    }

    /**
     * 장소 고정 API
     * @param scheduleId 일정 ID
     * @param pinPlaceRequestDTO 장소 고정 요청
     * @return  (status) 204
     */
    @PatchMapping(value = "/{scheduleId}/places/pin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> pinPlaces(@PathVariable String scheduleId, @RequestBody PinPlaceRequestDTO pinPlaceRequestDTO){
        // 장소 고정
        scheduleService.pinPlaces(scheduleId, pinPlaceRequestDTO);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 장소 제외 API
     * @param scheduleId 일정 ID
     * @param banPlaceRequestDTO 장소 제외 요청
     * @return  (status) 204
     */
    @PatchMapping(value = "/{scheduleId}/places/ban", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> banPlaces(@PathVariable String scheduleId, @RequestBody BanPlaceRequestDTO banPlaceRequestDTO){
        // 장소 제외
        scheduleService.banPlaces(scheduleId, banPlaceRequestDTO);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 일정 보관 API
     * @param scheduleId 일정 ID
     * @return  (status) 204
     */
    @PatchMapping(value = "/{scheduleId}/keep")
    public ResponseEntity<Void> keep(@PathVariable String scheduleId){
        // 일정 보관
        scheduleService.keep(scheduleId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/{scheduleId}/feedback", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> feedback(@PathVariable String scheduleId, @RequestBody @Valid ScheduleFeedbackRequestDTO feedbackRequestDTO){
        // 일정 피드백
        ScheduleReadResponseDTO scheduleDTO=scheduleService.feedback(scheduleId, feedbackRequestDTO);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(scheduleDTO));
    }

    @PatchMapping(value = "/{scheduleId}/recreate")
    public ResponseEntity<SuccessResponse> recreate(@PathVariable String scheduleId){
        // 일정 보관
        ScheduleReadResponseDTO scheduleDTO=scheduleService.recreate(scheduleId);

        // 응답 반환
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(scheduleDTO));
    }
}

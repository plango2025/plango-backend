package com.example.plango.schedule.service.impl;

import com.example.plango.common.url.UrlManager;
import com.example.plango.common.url.UrlPath;
import com.example.plango.schedule.dto.ScheduleCreateRequestDTO;
import com.example.plango.schedule.dto.ScheduleReadResponseDTO;
import com.example.plango.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final UrlManager urlManager;
    private final RestTemplate restTemplate;

    @Override
    public ScheduleReadResponseDTO createAISchedule(ScheduleCreateRequestDTO scheduleCreateRequestDTO){
        // URL
        String url=urlManager.getUrl(UrlPath.PYTHON_AI_SCHEDULE);

        // 헤더 설정
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 객체 설정
        HttpEntity<ScheduleCreateRequestDTO> httpEntity=new HttpEntity<>(scheduleCreateRequestDTO, headers);

        // Python 서버에 AI 일정 생성 요청 보내기
        ResponseEntity<ScheduleReadResponseDTO> response=restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                ScheduleReadResponseDTO.class
        );

        // 응답 반환
        return response.getBody();
    }
}

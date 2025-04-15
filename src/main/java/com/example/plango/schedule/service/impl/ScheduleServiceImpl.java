package com.example.plango.schedule.service.impl;

import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.url.UrlManager;
import com.example.plango.common.url.UrlPath;
import com.example.plango.schedule.dto.ScheduleCreateRequestDTO;
import com.example.plango.schedule.dto.ScheduleReadResponseDTO;
import com.example.plango.schedule.exception.AIScheduleGenerationException;
import com.example.plango.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final UrlManager urlManager;
    private final RestTemplate restTemplate;

    @Value("${plango.python.secret}")
    private String plangoAIAccessKey;


    @Override
    public ScheduleReadResponseDTO createAISchedule(ScheduleCreateRequestDTO scheduleCreateRequestDTO){
        // URL
        String url=urlManager.getUrl(UrlPath.PYTHON_AI_SCHEDULE_URL);

        // 헤더 설정
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Access Key 설정
        headers.set("X-PLANGO-AI-ACCESS-KEY", plangoAIAccessKey);

        // HTTP 객체 설정
        HttpEntity<ScheduleCreateRequestDTO> httpEntity=new HttpEntity<>(scheduleCreateRequestDTO, headers);

        try{
            // Python 서버에 AI 일정 생성 요청 보내기
            ResponseEntity<ScheduleReadResponseDTO> response=restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    httpEntity,
                    ScheduleReadResponseDTO.class
            );

            // 응답 반환
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.FORBIDDEN) {
                throw new AccessDeniedException("AI 서버 접근 권한이 없습니다.");
            }
            throw new AIScheduleGenerationException(ErrorCode.PYTHON_AI_ERROR, e.getResponseBodyAsString());

        } catch (HttpServerErrorException e){
            // AI 일정 생성 에러 발생하면 예외 처리
            throw new AIScheduleGenerationException(ErrorCode.PYTHON_AI_ERROR, e.getResponseBodyAsString());
        }
    }
}

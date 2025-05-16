package com.example.plango.schedule.service.impl;

import com.example.plango.auth.jwt.exception.TokenException;
import com.example.plango.common.exception.ErrorCode;
import com.example.plango.common.security.SecurityService;
import com.example.plango.common.url.UrlManager;
import com.example.plango.schedule.dto.*;
import com.example.plango.schedule.exception.AIScheduleGenerationException;
import com.example.plango.schedule.service.ScheduleService;
import com.example.plango.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
@Log4j2
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final UrlManager urlManager;
    private final WebClient webClient;
    private final SecurityService securityService;

    @Value("${plango.python.secret}")
    private String plangoAIAccessKey;

    private String getUserId() {
        try {
            UserInfo userInfo = securityService.getUserInfo();
            return userInfo.getId();
        } catch (TokenException e) {
            return null;
        }
    }

    @Override
    public ScheduleReadResponseDTO createSchedule(ScheduleCreateRequestDTO dto) {
        // URL 생성
        String url = String.format("%s/api/schedules", urlManager.getPythonDomain());

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-PLANGO-AI-ACCESS-KEY", plangoAIAccessKey);

        try {
            // WebClient를 사용하여 POST 요청 전송
            return webClient.post()
                    .uri(url)
                    .headers(h -> h.addAll(headers))
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(ScheduleReadResponseDTO.class)
                    .block();

        } catch (WebClientResponseException.Forbidden e) {
            // 권한 없음
            throw new AccessDeniedException("AI 서버 접근 권한이 없습니다.");
        } catch (WebClientResponseException e) {
            // 그 외 4xx, 5xx 예외 처리
            throw new AIScheduleGenerationException(ErrorCode.PYTHON_AI_ERROR, e.getResponseBodyAsString());
        }
    }

    @Override
    public void pinPlaces(String scheduleId, PinPlaceRequestDTO dto) {
        // URL 생성
        String url = String.format("%s/api/schedules/%s/places/pin", urlManager.getPythonDomain(), scheduleId);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-PLANGO-AI-ACCESS-KEY", plangoAIAccessKey);

        // 요청 바디 구성
        Map<String, Object> body = Map.of(
                "places", dto.getPlaces(),
                "user_id", getUserId()
        );

        try {
            // PATCH 요청 전송
            webClient.patch()
                    .uri(url)
                    .headers(h -> h.addAll(headers))
                    .bodyValue(body)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (WebClientResponseException.Forbidden e) {
            throw new AccessDeniedException("AI 서버 접근 권한이 없습니다.");
        } catch (WebClientResponseException e) {
            throw new AIScheduleGenerationException(ErrorCode.PYTHON_AI_ERROR, e.getResponseBodyAsString());
        }
    }

    @Override
    public void banPlaces(String scheduleId, BanPlaceRequestDTO dto) {
        // URL 생성
        String url = String.format("%s/api/schedules/%s/places/ban", urlManager.getPythonDomain(), scheduleId);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-PLANGO-AI-ACCESS-KEY", plangoAIAccessKey);

        // 요청 바디 구성
        Map<String, Object> body = Map.of(
                "places", dto.getPlaces(),
                "user_id", getUserId()
        );

        try {
            // PATCH 요청 전송
            webClient.patch()
                    .uri(url)
                    .headers(h -> h.addAll(headers))
                    .bodyValue(body)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (WebClientResponseException.Forbidden e) {
            throw new AccessDeniedException("AI 서버 접근 권한이 없습니다.");
        } catch (WebClientResponseException e) {
            throw new AIScheduleGenerationException(ErrorCode.PYTHON_AI_ERROR, e.getResponseBodyAsString());
        }
    }

    @Override
    public void keep(String scheduleId) {
        // URL 생성
        String url = String.format("%s/api/schedules/%s/keep", urlManager.getPythonDomain(), scheduleId);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-PLANGO-AI-ACCESS-KEY", plangoAIAccessKey);

        // 요청 바디 구성
        Map<String, String> body = Map.of("user_id", getUserId());

        try {
            // PATCH 요청 전송
            webClient.patch()
                    .uri(url)
                    .headers(h -> h.addAll(headers))
                    .bodyValue(body)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

        } catch (WebClientResponseException.Forbidden e) {
            throw new AccessDeniedException("AI 서버 접근 권한이 없습니다.");
        } catch (WebClientResponseException e) {
            throw new AIScheduleGenerationException(ErrorCode.PYTHON_AI_ERROR, e.getResponseBodyAsString());
        }
    }

    @Override
    public ScheduleReadResponseDTO feedback(String scheduleId, ScheduleFeedbackRequestDTO dto) {
        // URL 생성
        String url = String.format("%s/api/schedules/%s/feedback", urlManager.getPythonDomain(), scheduleId);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-PLANGO-AI-ACCESS-KEY", plangoAIAccessKey);

        // 요청 바디 구성
        Map<String, Object> body = Map.of(
                "feedback", dto.getFeedback(),
                "user_id", getUserId()
        );

        try {
            // PATCH 요청 전송 및 응답 수신
            return webClient.patch()
                    .uri(url)
                    .headers(h -> h.addAll(headers))
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(ScheduleReadResponseDTO.class)
                    .block();

        } catch (WebClientResponseException.Forbidden e) {
            throw new AccessDeniedException("AI 서버 접근 권한이 없습니다.");
        } catch (WebClientResponseException e) {
            throw new AIScheduleGenerationException(ErrorCode.PYTHON_AI_ERROR, e.getResponseBodyAsString());
        }
    }

    @Override
    public ScheduleReadResponseDTO recreate(String scheduleId) {
        // URL 생성
        String url = String.format("%s/api/schedules/%s/recreate", urlManager.getPythonDomain(), scheduleId);

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-PLANGO-AI-ACCESS-KEY", plangoAIAccessKey);

        // 요청 바디 구성
        Map<String, String> body = Map.of("user_id", getUserId());

        try {
            // PATCH 요청 전송 및 응답 수신
            return webClient.patch()
                    .uri(url)
                    .headers(h -> h.addAll(headers))
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(ScheduleReadResponseDTO.class)
                    .block();

        } catch (WebClientResponseException.Forbidden e) {
            throw new AccessDeniedException("AI 서버 접근 권한이 없습니다.");
        } catch (WebClientResponseException e) {
            throw new AIScheduleGenerationException(ErrorCode.PYTHON_AI_ERROR, e.getResponseBodyAsString());
        }
    }
}

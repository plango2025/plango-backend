package com.example.plango.place.service.impl;

import com.example.plango.common.security.SecurityService;
import com.example.plango.common.url.UrlManager;
import com.example.plango.place.exception.TourStreamingException;
import com.example.plango.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

@Service
@Log4j2
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {

    private final WebClient webClient;
    private final UrlManager urlManager;
    private final SecurityService securityService;

    @Value("${plango.python.secret}")
    private String plangoAIAccessKey;

    @Override
    public Flux<ServerSentEvent<String>> streamTourInfo(String keyword) {
        String url = String.format("%s/tour?keyword=%s", urlManager.getPythonDomain(), keyword);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-PLANGO-AI-ACCESS-KEY", plangoAIAccessKey);

        try {
            return webClient.get()
                    .uri(url)
                    .headers(h -> h.addAll(headers))
                    .accept(MediaType.APPLICATION_NDJSON)
                    .retrieve()
                    .bodyToFlux(String.class)
                    .map(data -> ServerSentEvent.builder(data).build());

        } catch (WebClientResponseException e) {
            log.error("Python /tour API 호출 실패: status={} body={}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new TourStreamingException("투어 정보를 불러오는 중 오류가 발생했습니다.", e);
        } catch (Exception e) {
            log.error("Python 서버 호출 중 알 수 없는 오류 발생", e);
            throw new TourStreamingException("서버 오류로 인해 데이터를 불러올 수 없습니다.", e);
        }
    }
}

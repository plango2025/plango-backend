package com.example.plango.place.controller;

import com.example.plango.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/api/place")
@RequiredArgsConstructor
@Log4j2
public class PlaceController {

    private final PlaceService tourService;

    /**
     * 클라이언트로부터 검색 키워드를 입력받아
     * Python 서버의 /tour API 결과를 그대로 스트리밍으로 반환합니다.
     *
     * @param keyword 검색 키워드
     * @return Flux<ServerSentEvent<String>> (preview → detail → llm_result 순)
     */
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamTourInfo(@RequestParam("keyword") @NotBlank @Size(max = 50) String keyword) {
        log.info("📨 /api/tour 요청 수신: keyword = {}", keyword);
        return tourService.streamTourInfo(keyword);
    }
}

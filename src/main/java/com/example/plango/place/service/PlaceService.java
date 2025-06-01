package com.example.plango.place.service;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

public interface PlaceService {

    /**
     * 키워드를 기반으로 Python 서버의 /tour API를 호출하고,
     * 응답 스트림을 클라이언트에 그대로 전달하는 역할을 합니다.
     *
     * @param keyword 클라이언트로부터 전달받은 검색 키워드
     * @return Python 서버의 StreamingResponse 스트림 (preview → detail → llm_result 순)
     */
    Flux<ServerSentEvent<String>> streamTourInfo(String keyword);
}

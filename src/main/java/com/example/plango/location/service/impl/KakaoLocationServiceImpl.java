package com.example.plango.location.service.impl;

import com.example.plango.common.url.UrlManager;
import com.example.plango.common.url.UrlPath;
import com.example.plango.location.dto.LocationSearchRequestDTO;
import com.example.plango.location.dto.KakaoLocationSearchResponseDTO;
import com.example.plango.location.service.KakaoLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoLocationServiceImpl implements KakaoLocationService {
    private final UrlManager urlManager;
    private final RestTemplate restTemplate;

    @Value("${kakao.client.id}")
    private String kakaoClientId;


    /**
     * 카카오 API를 이용해서 키워드에 따라 장소 검색
     * @param locationSearchRequestDTO 검색 요청 정보
     * @return 장소 검색 결과
     */
    @Override
    public KakaoLocationSearchResponseDTO searchLocationByKeyword(LocationSearchRequestDTO locationSearchRequestDTO) {
        String keyword = locationSearchRequestDTO.getKeyword();
        Integer page = locationSearchRequestDTO.getPage();
        Integer size = locationSearchRequestDTO.getSize();

        // URL 생성
        String kakaoLocationUrl=urlManager.getUrl(UrlPath.KAKAO_LOCATION_URL);
        StringBuilder sb=new StringBuilder(kakaoLocationUrl).append("?query=").append(keyword);
        if (page != null) sb.append("&page=").append(page);
        if (size != null) sb.append("&size=").append(size);
        String url = sb.toString();

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoClientId);

        // http 요청 (카카오 주소 검색 API)
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<KakaoLocationSearchResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, KakaoLocationSearchResponseDTO.class);

        return response.getBody();
    }
}


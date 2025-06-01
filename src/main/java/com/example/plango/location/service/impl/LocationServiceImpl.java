package com.example.plango.location.service.impl;

import com.example.plango.common.page.PageResponseDTO;
import com.example.plango.location.dto.LocationSearchRequestDTO;
import com.example.plango.location.dto.KakaoLocationSearchResponseDTO;
import com.example.plango.location.dto.KakaoLocationSearchResponseDTO.Document;
import com.example.plango.location.dto.KakaoLocationSearchResponseDTO.Meta;
import com.example.plango.location.dto.LocationSearchResponseDTO;
import com.example.plango.location.service.KakaoLocationService;
import com.example.plango.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final KakaoLocationService kakaoLocationService;

    /**
     * 키워드에 따라 장소 검색
     * @param locationSearchRequestDTO 검색 요청 정보
     * @return 장소 검색 결과
     */
    @Override
    public PageResponseDTO<LocationSearchResponseDTO> searchLocation(LocationSearchRequestDTO locationSearchRequestDTO) {
        // Kakao 장소 검색 API 요청
        KakaoLocationSearchResponseDTO kakaoLocationSearchResponseDTO =kakaoLocationService.searchLocationByKeyword(locationSearchRequestDTO);
        Meta meta= kakaoLocationSearchResponseDTO.getMeta();
        List<Document> documents= kakaoLocationSearchResponseDTO.getDocuments();

        // DTO 변환
        List<LocationSearchResponseDTO> locationSearchResponseDTOS =documents.stream().map((document) -> {
            return LocationSearchResponseDTO.builder()
                    .id(document.getId())
                    .placeName(document.getPlaceName())
                    .addressName(document.getAddressName())
                    .roadAddressName(document.getRoadAddressName())
                    .categoryName(document.getCategoryName())
                    .x(document.getX())
                    .y(document.getY())
                    .build();
        }).toList();

        // 페이지 응답 생성
        PageResponseDTO<LocationSearchResponseDTO> pageResponseDTO=PageResponseDTO.<LocationSearchResponseDTO>builder()
                .content(locationSearchResponseDTOS)
                .hasNext(!meta.isEnd())
                .totalCount(meta.getTotalCount())
                .build();

        return pageResponseDTO;
    }
}
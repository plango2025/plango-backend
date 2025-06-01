package com.example.plango.location.controller;

import com.example.plango.common.dto.SuccessResponse;
import com.example.plango.common.page.PageResponseDTO;
import com.example.plango.location.dto.LocationSearchRequestDTO;
import com.example.plango.location.dto.LocationSearchResponseDTO;
import com.example.plango.location.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/locations")
public class LocationController {
    private final LocationService locationService;


    /**
     * 카카오 장소 검색 API
     * @param locationSearchRequestDTO 장소 검색 요청 정보
     * @return  (status) 200,
     *          (body) 장소 검색 목록
     */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> search(@Valid @ModelAttribute LocationSearchRequestDTO locationSearchRequestDTO) {
        // 장소 검색
        PageResponseDTO<LocationSearchResponseDTO> pageResponseDTO=locationService.searchLocation(locationSearchRequestDTO);

        // 응답 생성
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.of(pageResponseDTO));
    }
}
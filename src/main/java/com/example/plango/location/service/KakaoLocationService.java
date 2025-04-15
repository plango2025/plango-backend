package com.example.plango.location.service;

import com.example.plango.location.dto.LocationSearchRequestDTO;
import com.example.plango.location.dto.KakaoLocationSearchResponseDTO;

public interface KakaoLocationService {
    KakaoLocationSearchResponseDTO searchLocationByKeyword(LocationSearchRequestDTO locationSearchRequestDTO);
}

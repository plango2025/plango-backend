package com.example.plango.location.service;

import com.example.plango.common.page.PageResponseDTO;
import com.example.plango.location.dto.LocationSearchRequestDTO;
import com.example.plango.location.dto.LocationSearchResponseDTO;

public interface LocationService {
    PageResponseDTO<LocationSearchResponseDTO> searchLocation(LocationSearchRequestDTO locationSearchRequestDTO);
}

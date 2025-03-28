package com.example.plango.common.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class SuccessResponse extends HashMap<String, Object> {

    private SuccessResponse(Object dto) {
        ObjectMapper mapper = new ObjectMapper();
        this.putAll(mapper.convertValue(dto, new TypeReference<Map<String, Object>>() {}));
    }

    public static SuccessResponse of(Object dto) {
        return new SuccessResponse(dto);
    }
}

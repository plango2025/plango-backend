package com.example.plango.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.example.plango.common.exception.ErrorCode;

public enum TargetType {

    SCHEDULE("SCHEDULE"),
    PLACE("PLACE"),
    SCHEDULE_REVIEW("SCHEDULE_REVIEW"),
    PLACE_REVIEW("PLACE_REVIEW"),
    COMMENT("COMMENT");

    private final String value;

    TargetType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TargetType fromValue(String value) {
        for (TargetType type : TargetType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }

        // 추후 plango 프로젝트에서 공통적으로 사용하고 있는 예외 처리 방식으로 교체 예정
        throw new IllegalArgumentException("Invalid TargetType: " + value);

    }
}

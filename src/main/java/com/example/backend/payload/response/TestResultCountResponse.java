package com.example.backend.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestResultCountResponse {
    private Long total;
    private Long negative;

    public TestResultCountResponse(Long total, Long negative) {
        this.total = total;
        this.negative = negative;
    }
}

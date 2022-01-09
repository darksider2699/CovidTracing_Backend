package com.example.backend.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class TestResultCountResponse {
    private LocalDate dateRecord;
    private Long total;
    private Long negative;

    public TestResultCountResponse(LocalDate dateRecord, Long total, Long negative) {
        this.dateRecord = dateRecord;
        this.total = total;
        this.negative = negative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestResultCountResponse)) return false;
        TestResultCountResponse that = (TestResultCountResponse) o;
        return Objects.equals(getDateRecord(), that.getDateRecord()) && Objects.equals(getTotal(), that.getTotal()) && Objects.equals(getNegative(), that.getNegative());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDateRecord(), getTotal(), getNegative());
    }
}

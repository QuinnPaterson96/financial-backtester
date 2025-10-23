package com.quinn.backtester.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class SeekRequest {
    private String targetDate;
    public Instant getTargetInstant() {
        return Instant.parse(targetDate + "T00:00:00Z");
    }
}

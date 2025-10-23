package com.quinn.backtester.dto;

import lombok.Data;

@Data
public class ReplayRequest {
    private String dataset;
    private String startDate;
    private String endDate;
    private double speed = 1.0;
}

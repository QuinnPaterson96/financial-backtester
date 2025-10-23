package com.quinn.backtester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


public class ReplayStatus {
    private Instant simTime;
    private double speed;
    private boolean paused;

    public ReplayStatus(Instant instant, double speed, boolean paused) {

    }
}

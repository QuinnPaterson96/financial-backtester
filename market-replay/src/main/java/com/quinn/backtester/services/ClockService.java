package com.quinn.backtester.services;

import com.quinn.backtester.model.ReplayStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@Slf4j
public class ClockService {
    private Instant t0Wall;
    private Instant T0Sim;
    private double speed = 1.0;
    private boolean paused = false;
    private Duration seekOffset = Duration.ZERO;

    public synchronized void start(Instant startSim, double speed) {
        this.T0Sim = startSim;
        this.t0Wall = Instant.now();
        this.speed = speed;
        this.paused = false;
        this.seekOffset = Duration.ZERO;
    }

    public synchronized Instant nowSim() {
        if (paused) return T0Sim.plus(seekOffset);
        Duration wallElapsed = Duration.between(t0Wall, Instant.now());
        return T0Sim.plus(seekOffset).plusMillis((long)(wallElapsed.toMillis() * speed));
    }

    public synchronized void pause() { paused = true; }
    public synchronized void resume() {
        paused = false;
        t0Wall = Instant.now();
    }
    public synchronized void setSpeed(double newSpeed) { this.speed = newSpeed; }
    public synchronized void seekTo(Instant targetSim) {
        seekOffset = Duration.between(T0Sim, targetSim);
    }

    public synchronized ReplayStatus status() {
        return new ReplayStatus(nowSim(), speed, paused);
    }
}

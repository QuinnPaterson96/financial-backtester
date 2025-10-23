package com.quinn.backtester.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;


public class MarketRecord {
    private Instant date;
    private String ticker;
    private double open, high, low, close, adjClose, volume;

    public MarketRecord(Instant date, String ticker, double open, double high, double low, double close, double adjClose, double volume) {
    }

    public static MarketRecord from(ResultSet rs) throws SQLException {
        return new MarketRecord(
                rs.getTimestamp("Date").toInstant(),
                rs.getString("Ticker"),
                rs.getDouble("Open"),
                rs.getDouble("High"),
                rs.getDouble("Low"),
                rs.getDouble("Close"),
                rs.getDouble("Adj Close"),
                rs.getDouble("Volume")
        );
    }

    public String toJson() {
        return String.format(
                "{\"date\":\"%s\",\"ticker\":\"%s\",\"close\":%.2f}",
                date, ticker, close
        );
    }
}


package com.quinn.backtester.services;

import org.springframework.stereotype.Service;
import java.sql.*;

@Service
public class ParquetReaderService {

    private static final String FILE_PATH = "data/parquet/equities_long.parquet";

    public void testRead() {
        try (Connection conn = DriverManager.getConnection("jdbc:duckdb:")) {
            String sql = String.format(
                    "SELECT Date, Ticker, Close, Volume FROM read_parquet('%s') LIMIT 5",
                    FILE_PATH
            );
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Sample rows from Parquet:");
            while (rs.next()) {
                System.out.printf("%s | %s | %.2f | %d%n",
                        rs.getString("Date"),
                        rs.getString("Ticker"),
                        rs.getDouble("Close"),
                        rs.getLong("Volume"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

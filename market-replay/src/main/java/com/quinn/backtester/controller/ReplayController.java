package com.quinn.backtester.controller;

import com.quinn.backtester.service.ParquetReaderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplayController {

    private final ParquetReaderService parquetReaderService;

    public ReplayController(ParquetReaderService parquetReaderService) {
        this.parquetReaderService = parquetReaderService;
    }

    @GetMapping("/replay/test")
    public String testReplay() {
        parquetReaderService.testRead();
        return "✅ Parquet read successful — check console logs.";
    }
}

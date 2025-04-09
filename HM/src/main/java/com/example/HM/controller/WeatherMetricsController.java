package com.example.HM.controller;

import com.example.HM.service.WeatherForecastService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherMetricsController {

    private final WeatherForecastService weatherService;

    public WeatherMetricsController(WeatherForecastService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/metrics")
    public Map<String, Integer> getMetrics() {
        return Map.of(
            "totalRequests", weatherService.getTotalRequests(),
            "cacheHits", weatherService.getCacheHits(),
            "cacheMisses", weatherService.getCacheMisses()
        );
    }
}

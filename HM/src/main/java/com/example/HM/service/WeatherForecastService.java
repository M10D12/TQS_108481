package com.example.HM.service;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

@Service
public class WeatherForecastService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<LocalDate, CachedWeather> cache = new HashMap<>();
    private static final long TTL_MILLIS = 1000 * 60 * 60; 
    private int totalRequests = 0;
    private int cacheHits = 0;
    private int cacheMisses = 0;

    public String getForecast(LocalDate date) {
        totalRequests++;

        CachedWeather cached = cache.get(date);
        if (cached != null && !cached.isExpired()) {
            cacheHits++;
            return cached.weather;
        }

        cacheMisses++;

        String url = "https://api.open-meteo.com/v1/forecast"
                + "?latitude=40.64&longitude=-8.65"
                + "&daily=weathercode&timezone=auto"
                + "&start_date=" + date
                + "&end_date=" + date;

        try {
            Map response = restTemplate.getForObject(url, Map.class);
            Map daily = (Map) response.get("daily");

            List<String> dates = (List<String>) daily.get("time");
            List<Integer> codes = (List<Integer>) daily.get("weathercode");

            if (dates != null && codes != null && !dates.isEmpty() && !codes.isEmpty()) {
                int code = codes.get(0);
                String summary = interpretWeatherCode(code);
                cache.put(date, new CachedWeather(summary, System.currentTimeMillis()));
                return summary;
            }

            return "Sem previsão disponível";

        } catch (Exception e) {
            return "Erro na previsão: " + e.getMessage();
        }
    }

    private String interpretWeatherCode(int code) {
        return switch (code) {
            case 0 -> "☀️ Céu limpo";
            case 1, 2 -> "🌤️ Parcialmente nublado";
            case 3 -> "☁️ Nublado";
            case 45, 48 -> "🌫️ Nevoeiro";
            case 51, 53, 55 -> "🌦️ Chuva ligeira";
            case 61, 63, 65 -> "🌧️ Chuva moderada";
            case 80, 81, 82 -> "🌧️ Aguaceiros";
            case 95 -> "⛈️ Trovoada";
            case 96, 99 -> "⛈️ Trovoada com granizo";
            default -> "🌈 Código desconhecido (" + code + ")";
        };
    }

    private static class CachedWeather {
        String weather;
        long timestamp;

        CachedWeather(String weather, long timestamp) {
            this.weather = weather;
            this.timestamp = timestamp;
        }

        boolean isExpired() {
            return System.currentTimeMillis() - timestamp > TTL_MILLIS;
        }
    }


    public int getTotalRequests() {
        return totalRequests;
    }
    
    public int getCacheHits() {
        return cacheHits;
    }
    
    public int getCacheMisses() {
        return cacheMisses;
    }
    
}

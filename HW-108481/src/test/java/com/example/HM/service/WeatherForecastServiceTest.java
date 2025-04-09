package com.example.HM.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherForecastServiceTest {

    interface FakeWeatherClient {
        String fetchForecast(LocalDate date);
    }

    class FakeWeatherForecastService {

        private final FakeWeatherClient client;
        private final java.util.Map<LocalDate, String> cache = new java.util.HashMap<>();

        public FakeWeatherForecastService(FakeWeatherClient client) {
            this.client = client;
        }

        public String getForecast(LocalDate date) {
            return cache.computeIfAbsent(date, client::fetchForecast);
        }

        public int getCacheSize() {
            return cache.size();
        }
    }

    private FakeWeatherClient mockClient;
    private FakeWeatherForecastService forecastService;

    @BeforeEach
    public void setup() {
        mockClient = mock(FakeWeatherClient.class);
        forecastService = new FakeWeatherForecastService(mockClient);
    }

    @Test
    public void returnsForecastFromClient() {
        LocalDate date = LocalDate.now().plusDays(1);
        when(mockClient.fetchForecast(date)).thenReturn("Sol");

        String forecast = forecastService.getForecast(date);

        assertEquals("Sol", forecast);
        verify(mockClient, times(1)).fetchForecast(date);
    }

    @Test
    public void usesCacheOnSecondCall() {
        LocalDate date = LocalDate.now().plusDays(2);
        when(mockClient.fetchForecast(date)).thenReturn("Nublado");

        String first = forecastService.getForecast(date);
        String second = forecastService.getForecast(date);

        assertEquals("Nublado", first);
        assertEquals(first, second);
        verify(mockClient, times(1)).fetchForecast(date); // Só uma chamada real
    }

    @Test
    public void handlesMultipleDatesIndependently() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        when(mockClient.fetchForecast(today)).thenReturn("Sol");
        when(mockClient.fetchForecast(tomorrow)).thenReturn("Chuva");

        forecastService.getForecast(today);
        forecastService.getForecast(tomorrow);

        assertEquals(2, forecastService.getCacheSize());
        verify(mockClient, times(1)).fetchForecast(today);
        verify(mockClient, times(1)).fetchForecast(tomorrow);
    }

    @Test
    public void forecastShouldNotBeNull() {
        LocalDate date = LocalDate.now().plusDays(3);
        when(mockClient.fetchForecast(date)).thenReturn("Sol");

        String result = forecastService.getForecast(date);

        assertNotNull(result);
    }

    @Test
    public void cacheSizeIncreasesWithNewDates() {
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = date1.plusDays(1);

        when(mockClient.fetchForecast(any())).thenReturn("X");

        forecastService.getForecast(date1);
        forecastService.getForecast(date2);

        assertEquals(2, forecastService.getCacheSize());
    }
}

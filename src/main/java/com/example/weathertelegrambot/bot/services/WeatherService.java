package com.example.weathertelegrambot.bot.services;

import com.example.weathertelegrambot.bot.exceptions.ApiException;
import com.example.weathertelegrambot.bot.models.Weather;

import java.io.IOException;
import java.io.InputStream;


public interface WeatherService {
    Weather getWeather(double latitude, double longitude) throws ApiException;

    InputStream getWeatherIcon(String url) throws IOException;
}

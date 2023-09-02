package com.example.weathertelegrambot.bot.services;

import com.example.weathertelegrambot.bot.dto.SetUserLocationDto;
import com.example.weathertelegrambot.bot.models.UserLocationEntity;


public interface UserLocationService {
    UserLocationEntity findUserLocationById(Long id);
    UserLocationEntity setUserLocationById(Long id, SetUserLocationDto userLocationDto);
}

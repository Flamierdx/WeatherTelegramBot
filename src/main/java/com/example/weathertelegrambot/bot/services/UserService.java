package com.example.weathertelegrambot.bot.services;

import com.example.weathertelegrambot.bot.dto.SetUserDto;
import com.example.weathertelegrambot.bot.models.UserEntity;

import java.util.Optional;


public interface UserService {
    Optional<UserEntity> findUserById(Long id);

    UserEntity findUserByIdOrThrow(Long id);

    void setUserById(Long id, SetUserDto userLocationDto);
}

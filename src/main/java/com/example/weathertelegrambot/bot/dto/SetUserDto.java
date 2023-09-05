package com.example.weathertelegrambot.bot.dto;

import com.example.weathertelegrambot.bot.models.UserState;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode
@Value
public class SetUserDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 0L;
    Double latitude;
    Double longitude;
    UserState state;
}
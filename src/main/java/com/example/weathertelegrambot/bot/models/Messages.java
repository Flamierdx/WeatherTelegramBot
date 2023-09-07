package com.example.weathertelegrambot.bot.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Messages {
    NO_LOCATION("You do not have a location yet.\nPlease set your location with /set_location command.");

    private final String message;
}

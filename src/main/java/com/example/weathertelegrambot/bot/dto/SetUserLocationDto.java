package com.example.weathertelegrambot.bot.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Value
public record SetUserLocationDto(@NotNull Double latitude, @NotNull Double longitude) implements Serializable {
}
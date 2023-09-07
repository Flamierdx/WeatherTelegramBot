package com.example.weathertelegrambot.bot.handlers;

import com.example.weathertelegrambot.bot.TelegramBot;
import com.example.weathertelegrambot.bot.exceptions.ApiException;
import com.example.weathertelegrambot.bot.models.Messages;
import com.example.weathertelegrambot.bot.models.UserEntity;
import com.example.weathertelegrambot.bot.models.Weather;
import com.example.weathertelegrambot.bot.services.UserService;
import com.example.weathertelegrambot.bot.services.WeatherService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Arrays;


@Log4j2
@AllArgsConstructor
@Component
public class WeatherUpdateHandler implements UpdateHandler {
    private WeatherService weatherService;
    private UserService userService;
    private static final String[] WEATHER_COMMANDS = {"/weather"};

    @Override
    public void handle(Update update, TelegramBot bot) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();

        try {
            UserEntity userEntity = userService.findUserByIdOrThrow(chatId);
            Weather weather = weatherService.getWeather(
                    userEntity.getLocationLatitude(),
                    userEntity.getLocationLongitude()
            );

            bot.sendPhoto(
                    chatId,
                    weatherService.getWeatherIcon(weather.getConditionIcon()),
                    createWeatherMessage(weather)
            );
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage(), e);
            bot.sendMessage(chatId, Messages.NO_LOCATION.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (ApiException e) {
            log.error(e.getMessage(), e);
            bot.sendMessage(chatId, e.getMessage());
        }
    }

    @Override
    public boolean isMatch(Update update) {
        return update.hasMessage() &&
               update.getMessage().hasText() &&
               Arrays.asList(WEATHER_COMMANDS).contains(update.getMessage().getText());
    }

    private String createWeatherMessage(Weather weather) {
        return String.format(
                "%s, %s, %s.\nCondition: %s.\nTemperature: %.2f.",
                weather.getLocationName(),
                weather.getLocationRegion(),
                weather.getLocationCountry(),
                weather.getConditionText(),
                weather.getTemperature()
        );
    }
}

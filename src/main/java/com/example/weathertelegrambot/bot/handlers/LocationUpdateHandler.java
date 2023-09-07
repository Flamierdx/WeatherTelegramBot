package com.example.weathertelegrambot.bot.handlers;

import com.example.weathertelegrambot.bot.TelegramBot;
import com.example.weathertelegrambot.bot.dto.SetUserDto;
import com.example.weathertelegrambot.bot.exceptions.WrongStateException;
import com.example.weathertelegrambot.bot.models.Messages;
import com.example.weathertelegrambot.bot.models.UserEntity;
import com.example.weathertelegrambot.bot.models.UserState;
import com.example.weathertelegrambot.bot.services.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Optional;


@Log4j2
@Component
@AllArgsConstructor
public class LocationUpdateHandler implements UpdateHandler {
    private static final String[] LOCATION_COMMANDS = {"/get_location", "/set_location"};

    private final UserService userService;

    @Override
    public void handle(Update update, TelegramBot bot) throws TelegramApiException, WrongStateException {
        Long chatId = update.getMessage().getChatId();
        if (update.getMessage().hasLocation()) {
            handleLocationUpdate(chatId, update, bot);
            return;
        }

        switch (update.getMessage().getText()) {
            case "/get_location" -> handleGetLocationUpdate(chatId, bot);
            case "/set_location" -> handleSetLocationUpdate(chatId, bot);
        }
    }

    @Override
    public boolean isMatch(Update update) {
        return update.hasMessage() && (
                update.getMessage().hasLocation() ||
                update.getMessage().hasText() &&
                Arrays.asList(LOCATION_COMMANDS).contains(update.getMessage().getText())
        );
    }

    private void handleGetLocationUpdate(Long chatId, TelegramBot bot) throws WrongStateException, TelegramApiException {
        Optional<UserEntity> optionalUserEntity = userService.findUserById(chatId);

        if (isUserWithoutLocation(optionalUserEntity)) {
            throw new WrongStateException(Messages.NO_LOCATION.getMessage());
        }

        UserEntity userEntity = optionalUserEntity.get();

        bot.sendMessage(chatId, "Your location:");
        bot.sendLocation(chatId, userEntity.getLocationLatitude(), userEntity.getLocationLongitude());
    }

    private void handleSetLocationUpdate(Long chatId, TelegramBot bot) throws TelegramApiException {
        userService.setUserById(chatId, new SetUserDto(
                null,
                null,
                UserState.SETTING_LOCATION)
        );
        bot.sendMessage(chatId, "Send your location.");
    }

    private void handleLocationUpdate(Long chatId, Update update, TelegramBot bot) throws TelegramApiException {
        Location location = update.getMessage().getLocation();

        userService.setUserById(chatId, new SetUserDto(
                location.getLatitude(),
                location.getLongitude(),
                UserState.WITH_LOCATION)
        );

        bot.sendMessage(chatId, "Location has been set.");
    }

    private boolean isUserWithoutLocation(Optional<UserEntity> userEntity) {
        return userEntity.isEmpty() || !userEntity.get().getState().equals(UserState.WITH_LOCATION);
    }
}

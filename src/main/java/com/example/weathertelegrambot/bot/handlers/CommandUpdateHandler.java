package com.example.weathertelegrambot.bot.handlers;

import com.example.weathertelegrambot.bot.TelegramBot;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;


@Log4j2
@AllArgsConstructor
@Component
public class CommandUpdateHandler implements UpdateHandler {
    private static final String[] DEFAULT_COMMANDS = {"/start", "/help"};

    @Override
    public void handle(Update update, TelegramBot bot) throws TelegramApiException {
        switch (update.getMessage().getText()) {
            case "/start" -> handleStartCommand(update, bot);
            case "/help" -> handleHelpCommand(update, bot);
        }
    }

    @Override
    public boolean isMatch(Update update) {
        return update.hasMessage() &&
               update.getMessage().hasText() &&
               Arrays.asList(DEFAULT_COMMANDS).contains(update.getMessage().getText());
    }

    private void handleHelpCommand(Update update, TelegramBot bot) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        String helpMessage = """
                Command list:
                /start - sending start message.
                /help - get list of commands for WeatherBot.
                /set_location - set your location for getting weather.
                /get_location - get your current location.
                /weather - get weather on your location.
                """;

        bot.sendMessage(chatId, helpMessage);
    }

    private void handleStartCommand(Update update, TelegramBot bot) throws TelegramApiException {
        Long chatId = update.getMessage().getChatId();
        String helloMessage = "Hello from WeatherBot.\nYou can find more information with /help command.";

        bot.sendMessage(chatId, helloMessage);
    }
}

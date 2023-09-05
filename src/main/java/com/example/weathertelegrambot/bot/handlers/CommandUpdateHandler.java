package com.example.weathertelegrambot.bot.handlers;

import com.example.weathertelegrambot.bot.TelegramBot;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;


@Log4j2
@AllArgsConstructor
@Component
public class CommandUpdateHandler implements UpdateHandler {
    private static final String[] DEFAULT_COMMANDS = {"/start", "/help"};

    @Override
    public void handle(Update update, TelegramBot bot) {

    }

    @Override
    public boolean isMatch(Update update) {
        return update.hasMessage() &&
               update.getMessage().hasText() &&
               Arrays.asList(DEFAULT_COMMANDS).contains(update.getMessage().getText());
    }
}

package com.example.weathertelegrambot.bot.handlers;

import com.example.weathertelegrambot.bot.TelegramBot;
import com.example.weathertelegrambot.bot.exceptions.WrongStateException;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface UpdateHandler {
    void handle(Update update, TelegramBot bot) throws WrongStateException, TelegramApiException;

    boolean isMatch(Update update);
}

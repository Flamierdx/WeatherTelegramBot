package com.example.weathertelegrambot;

import com.example.weathertelegrambot.bot.TelegramBot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class WeatherTelegramBotApplication {
    private static final Logger log = LogManager.getLogger(WeatherTelegramBotApplication.class);

    public static void main(String[] args) throws TelegramApiException {
        var context = SpringApplication.run(WeatherTelegramBotApplication.class, args);
        var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(context.getBean(TelegramBot.class));
        log.debug("TelegramBot has been registered.");
    }
}


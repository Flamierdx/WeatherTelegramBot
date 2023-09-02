package com.example.weathertelegrambot;

import com.example.weathertelegrambot.bot.TelegramBot;
import com.example.weathertelegrambot.bot.services.UserLocationService;
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
        log.info("Spring application started.");

        var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(context.getBean(TelegramBot.class));
        log.info("Telegram bot has been registered.");

        System.out.println(context.getBean(UserLocationService.class).findUserLocationById(1L));
    }
}


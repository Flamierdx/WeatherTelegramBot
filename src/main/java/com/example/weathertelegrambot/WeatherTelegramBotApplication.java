package com.example.weathertelegrambot;

import com.example.weathertelegrambot.bot.TelegramBot;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Log4j2
@SpringBootApplication
public class WeatherTelegramBotApplication {

    public static void main(String[] args) throws TelegramApiException {
        var context = SpringApplication.run(WeatherTelegramBotApplication.class, args);
        log.info("Spring application started.");

        var telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(context.getBean(TelegramBot.class));
        log.info("Telegram bot has been registered.");
    }
}


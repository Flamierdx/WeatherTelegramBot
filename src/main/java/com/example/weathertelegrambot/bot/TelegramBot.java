package com.example.weathertelegrambot.bot;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;


@Log4j2
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;

    private final BotDispatcher dispatcher;

    public TelegramBot(@Value("${bot.token}") String botToken, BotDispatcher dispatcher) {
        super(botToken);
        this.dispatcher = dispatcher;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            log.info("Update from telegram: " + update.toString());
            this.dispatcher.dispatch(update, this);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void sendMessage(Long id, String message) throws TelegramApiException {
        SendMessage sendMessageMethod = SendMessage
                .builder()
                .chatId(id)
                .text(message)
                .build();
        this.execute(sendMessageMethod);
    }

    public void sendLocation(Long id, Double latitude, Double longitude) throws TelegramApiException {
        SendLocation sendLocationMethod = SendLocation
                .builder()
                .chatId(id)
                .longitude(longitude)
                .latitude(latitude)
                .build();
        this.execute(sendLocationMethod);
    }

    public void sendPhoto(Long id, InputStream photoStream, String caption) throws TelegramApiException {
        InputFile inputFile = new InputFile(photoStream, "icon.png");
        SendPhoto sendPhotoMethod = SendPhoto
                .builder()
                .chatId(id)
                .photo(inputFile)
                .caption(caption)
                .build();

        this.execute(sendPhotoMethod);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}

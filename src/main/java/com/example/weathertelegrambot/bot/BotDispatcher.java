package com.example.weathertelegrambot.bot;

import com.example.weathertelegrambot.bot.exceptions.WrongStateException;
import com.example.weathertelegrambot.bot.handlers.UpdateHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Log4j2
@AllArgsConstructor
@Component
public class BotDispatcher {
    private UpdateHandler[] updateHandlers;

    public void dispatch(Update update, TelegramBot bot) {
        try {
            for (UpdateHandler handler : updateHandlers) {
                if (handler.isMatch(update)) {
                    handler.handle(update, bot);
                }
            }
        } catch (WrongStateException e) {
            try {
                bot.sendMessage(update.getMessage().getChatId(), e.getMessage());
            } catch (TelegramApiException ex) {
                log.error(e.getMessage(), ex);
            }
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);

        }
    }
}

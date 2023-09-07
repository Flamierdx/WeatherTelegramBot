package com.example.weathertelegrambot.bot.exceptions;


public class WrongStateException extends Exception {
    public WrongStateException(String message) {
        super(message);
    }

    public WrongStateException(String message, Throwable cause) {
        super(message, cause);
    }
}

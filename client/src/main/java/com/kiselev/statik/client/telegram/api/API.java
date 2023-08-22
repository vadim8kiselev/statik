package com.kiselev.statik.client.telegram.api;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class API {

    public static void sendMessage(
            TelegramLongPollingBot bot,
            Long id,
            String text) {
        try {
            bot.execute(SendMessage.builder()
                    .chatId(id)
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.kiselev.statik.client.telegram;

import com.kiselev.statik.client.telegram.api.API;
import com.kiselev.statik.client.telegram.handler.Handler;
import com.kiselev.statik.service.communication.Notifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class Telegram implements Notifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(Telegram.class);

    private final TelegramLongPollingBot bot;

    @Autowired
    public Telegram(
            Handler handler,
            @Value("${telegram.bot.username}") String username,
            @Value("${telegram.bot.token}") String token) {
        LOGGER.info("Initializing Telegram bot...");
        try {
            this.bot = new TelegramLongPollingBot() {
                @Override
                public void onUpdateReceived(Update update) {
                    handler.handle(
                            update,
                            response -> API.sendMessage(bot, update.getMessage().getChatId(), response));
                }

                @Override
                public String getBotUsername() {
                    return username;
                }

                @Override
                public String getBotToken() {
                    return token;
                }
            };

            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(bot);
        } catch (TelegramApiException exception) {
            LOGGER.info("Telegram bot initialization failed.");
            throw new RuntimeException(exception.getMessage(), exception);
        }
        LOGGER.info("Telegram bot successfully initialized.");
    }

    @Override
    public void notify(Long id, String text) {
        API.sendMessage(bot, id, text);
    }
}

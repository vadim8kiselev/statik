package com.kiselev.statik.client.telegram.handler;

import com.kiselev.statik.model.Command;
import com.kiselev.statik.model.Event;
import com.kiselev.statik.service.analytics.Analyzer;
import com.kiselev.statik.service.processor.Processor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class Handler {

    private final Processor processor;

    private final Analyzer analyzer;

    public void handle(Update update, Callback callback) {
        // TODO: Parse events & commands
        Message message = update.getMessage();

        Long chatId = message.getChatId();
        Integer date = message.getDate();
        String text = message.getText();
        MessageType type = preRecognize(text);

        switch (type) {
            case EVENT:
                String response = processor.process(
                        Event.builder()
                                .id(chatId)
                                .timestamp(Date.from(Instant.ofEpochSecond(date)))
                                .message(text)
                                .build());
                callback.call(response);
                return;
            case COMMAND:
                String report = analyzer.analyze(
                        Command.builder()
                                .id(chatId)
                                .message(text.substring(1))
                                .build());
                callback.call(report);
        }
    }

    private MessageType preRecognize(String text) {
        return text.startsWith("/") ? MessageType.COMMAND : MessageType.EVENT;
    }

    private enum MessageType {
        EVENT,
        COMMAND
    }

    @FunctionalInterface
    public interface Callback {
        void call(String response);
    }
}

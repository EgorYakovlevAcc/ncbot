package com.ncquizbot.ncbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class BotInitializer {
    private Bot bot;
    private TelegramBotsApi telegramBotsApi;

    @Autowired
    public BotInitializer(Bot bot, TelegramBotsApi telegramBotsApi) {
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException ex) {
            ex.printStackTrace();
        }
    }
}

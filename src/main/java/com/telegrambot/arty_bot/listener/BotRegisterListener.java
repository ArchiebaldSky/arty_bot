package com.telegrambot.arty_bot.listener;

import com.telegrambot.arty_bot.service.telegram.ArtyBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//@Component
@RequiredArgsConstructor
@Slf4j
public class BotRegisterListener {

    private final ArtyBot artyBot;
    private final TelegramBotsApi telegramBotsApi;
    private final ConfigurableApplicationContext ctx;

    @EventListener
    public void handle(ContextStartedEvent event){
        try {
            telegramBotsApi.registerBot(artyBot);
            log.info("Bot registered");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

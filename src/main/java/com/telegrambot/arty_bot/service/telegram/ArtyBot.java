package com.telegrambot.arty_bot.service.telegram;

import com.telegrambot.arty_bot.config.TelegramBotProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.methods.menubutton.SetChatMenuButton;
import org.telegram.telegrambots.meta.api.objects.ChatLocation;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButton;
import org.telegram.telegrambots.meta.api.objects.menubutton.MenuButtonWebApp;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.function.BiConsumer;

import static com.telegrambot.arty_bot.service.telegram.Constants.*;
import static org.telegram.abilitybots.api.objects.Locality.*;
import static org.telegram.abilitybots.api.objects.Privacy.*;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
@Slf4j
@SuppressWarnings("unused")
public class ArtyBot extends AbilityBot {

    private final long creatorId = 1493617120L;
    private final TelegramBotProperties properties;
    private final ResponseHandler responseHandler;

    protected ArtyBot(TelegramBotProperties properties) {
        super(properties.getToken(), properties.getName());
        log.info("Token: {}", properties.getToken());
        log.info("Name: {}", properties.getName());
        this.properties = properties;
        this.responseHandler = new ResponseHandler(silent, db);
    }

    @Override
    public long creatorId() {
        return creatorId;
    }

    public Ability startBot() {
        return Ability
                .builder()
                .name("start")
                .info(START_DESCRIPTION)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {responseHandler.replyToStart(ctx.chatId());
                    initBotMenu(ctx.chatId());
                })
                .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action = (abilityBot, upd) -> responseHandler.replyToButtons(upd);
        return Reply.of(action, Flag.TEXT, upd -> responseHandler.userIsActive(getChatId(upd)));
    }

    public Reply replyToCallBackQuery() {
        BiConsumer<BaseAbilityBot, Update> action = ((abilityBot, upd) -> responseHandler.replyToMessageButtons(upd));
        return Reply.of(action, Flag.CALLBACK_QUERY, upd -> responseHandler.userIsActive(getChatId(upd)));
    }

    public void initBotMenu(long chatId) {
        MenuButton button = MenuButtonWebApp.builder()
                .text(REFUEL)
                .webAppInfo(WebAppInfo.builder()
                        .url(MINI_APP_URL)
                        .build())
                .build();
        try {
            this.execute(SetChatMenuButton.builder().menuButton(button).chatId(chatId).build());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

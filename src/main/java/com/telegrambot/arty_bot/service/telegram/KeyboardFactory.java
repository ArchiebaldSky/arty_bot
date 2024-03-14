package com.telegrambot.arty_bot.service.telegram;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.telegrambot.arty_bot.service.telegram.Constants.*;


@RequiredArgsConstructor
public class KeyboardFactory {

    public InlineKeyboardMarkup getMiniAppKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(Collections.singletonList(InlineKeyboardButton.builder()
                .text(REFUEL)
                .webApp(WebAppInfo.builder()
                        .url(MINI_APP_URL)
                        .build())
                .build()));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getStartKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        keyboard.add(Collections.singletonList(InlineKeyboardButton.builder()
                .text(REFUEL)
                .webApp(WebAppInfo.builder()
                        .url(MINI_APP_URL)
                        .build())

                .build()));

        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}

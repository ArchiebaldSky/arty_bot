package com.telegrambot.arty_bot.service.telegram;

import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.Map;

import static com.telegrambot.arty_bot.service.telegram.Constants.*;
import static com.telegrambot.arty_bot.service.telegram.UserState.*;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class ResponseHandler {

    private final SilentSender sender;
    private final Map<Long, UserState> chatStates;
    private final KeyboardFactory keyboardFactory;

    public ResponseHandler(SilentSender sender, DBContext db) {
        this.sender = sender;
        chatStates = db.getMap(CHAT_STATES);
        chatStates.clear();
        this.keyboardFactory = new KeyboardFactory();
    }

    public void replyToStart(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(HELLO_MESSAGE);
        message.setReplyMarkup(keyboardFactory.getMiniAppKeyboard());
        sender.execute(message);
        chatStates.put(chatId, UNDEFINED);
    }

    public void replyToButtons(Update update) {
        long chatId = getChatId(update);
        Message message = update.getMessage();
        if (message.getText().equalsIgnoreCase("/stop")) {
            stopChat(chatId);
        } else if(message.getText().equalsIgnoreCase("/start")){
            replyToStart(chatId);
            return;
        }

        switch (chatStates.get(chatId)) {
            default -> replyToFunctionSelection(chatId, message);
        }
    }
    public void replyToMessageButtons(Update update) {
        long chatId = getChatId(update);
        CallbackQuery callbackQuery = update.getCallbackQuery();

        switch (chatStates.get(chatId)) {
            default -> unexpectedMessage(chatId);
        }
    }

    private void replyToFunctionSelection(long chatId, Message message) {
        message.getLocation();
        switch (message.getText()) {
            default -> unexpectedMessage(chatId);
        }
    }

    public void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(STOP_MESSAGE);
        chatStates.remove(chatId);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        sender.execute(sendMessage);
    }

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }

    private void editMessage(long chatId, int messageId, String text, InlineKeyboardMarkup markup) {
        EditMessageText message = new EditMessageText();
        message.setChatId(chatId);
        message.setMessageId(messageId);
        message.setText(text);
        message.setReplyMarkup(markup);
        message.enableMarkdown(true);
        message.disableWebPagePreview();
        sender.execute(message);
    }

    private void unexpectedMessage(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(UNEXPECTED_MESSAGE);
        sender.execute(sendMessage);
    }
}

package by.wadikk.telegrambot.service;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;

public interface SendMessageBotService {
    BotApiMethod<?> sendMessage(Long chatId, String message);
}

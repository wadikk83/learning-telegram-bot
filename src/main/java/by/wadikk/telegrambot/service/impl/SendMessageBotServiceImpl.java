package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.service.SendMessageBotService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SendMessageBotServiceImpl implements SendMessageBotService {
    @Override
    public BotApiMethod<?> sendMessage(Long chatId, String message) {
        if (isBlank(message)) return null;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());

        //todo проверить на UI
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        return sendMessage;
    }
}

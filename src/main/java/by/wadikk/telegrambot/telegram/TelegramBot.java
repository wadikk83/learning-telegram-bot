package by.wadikk.telegrambot.telegram;

import by.wadikk.telegrambot.handler.CallbackQueryHandler;
import by.wadikk.telegrambot.handler.MessageHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
@Slf4j
public class TelegramBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;

    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;


    public TelegramBot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            if (update.getMessage() != null) {
                return messageHandler.newAnswerMessage(update);
            }
        }
        //todo обработать
        log.error("Null message and no has CallbackQuery");
        return null;
    }
}

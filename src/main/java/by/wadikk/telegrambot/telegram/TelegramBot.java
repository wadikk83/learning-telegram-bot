package by.wadikk.telegrambot.telegram;

import by.wadikk.telegrambot.config.TelegramConfig;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Getter
@Setter
public class TelegramBot extends SpringWebhookBot {

    private final TelegramConfig botConfig;

    private final TelegramFacade telegramFacade;

    public TelegramBot(TelegramFacade telegramFacade, SetWebhook setWebhook, TelegramConfig botConfig) {
        super(setWebhook, botConfig.getBotToken());
        this.botConfig = botConfig;
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return botConfig.getWebHookPath();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
}

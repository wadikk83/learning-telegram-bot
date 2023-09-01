package by.wadikk.telegrambot.config;

import by.wadikk.telegrambot.handler.CallbackQueryHandler;
import by.wadikk.telegrambot.handler.MessageHandler;
import by.wadikk.telegrambot.telegram.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@AllArgsConstructor
public class SpringConfig {

    private final TelegramConfig telegramConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder()
                .url(telegramConfig.getWebhookPath())
                .build();
    }

    @Bean
    public TelegramBot bot(SetWebhook setWebhook,
                           MessageHandler messageHandler,
                           CallbackQueryHandler callbackQueryHandler) {
        TelegramBot bot = new TelegramBot(setWebhook,
                telegramConfig.getBotToken(),
                messageHandler,
                callbackQueryHandler);
        bot.setBotPath(telegramConfig.getWebhookPath());
        bot.setBotUsername(telegramConfig.getBotName());
        return bot;
    }
}

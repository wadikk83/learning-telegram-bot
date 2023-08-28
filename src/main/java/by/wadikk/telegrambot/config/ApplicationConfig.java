package by.wadikk.telegrambot.config;

import by.wadikk.telegrambot.telegram.TelegramBot;
import by.wadikk.telegrambot.telegram.TelegramFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
public class ApplicationConfig {

    private final TelegramConfig botConfig;

    public ApplicationConfig(TelegramConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebHookPath()).build();
    }

    @Bean
    public TelegramBot springWebHookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        TelegramBot bot = new TelegramBot(telegramFacade, setWebhook, botConfig);
        return bot;
    }

}

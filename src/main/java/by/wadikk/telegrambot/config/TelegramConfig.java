package by.wadikk.telegrambot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TelegramConfig {

    @Value("${telegram.webhook-path}")
    String webHookPath;

    @Value("${telegram.bot-name}")
    String botName;

    @Value("${telegram.bot-token}")
    String botToken;

}

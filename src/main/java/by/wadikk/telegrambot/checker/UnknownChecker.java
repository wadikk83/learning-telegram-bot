package by.wadikk.telegrambot.checker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Slf4j
@Component
public class UnknownChecker implements Checker {
    @Override
    public BotApiMethod<?> check(CallbackQuery buttonQuery) {
        log.error("No found checker");
        String chatId = buttonQuery.getMessage().getChatId().toString();
        return new SendMessage(chatId, "Не могу проверить ответ. Попробуйте попозже");
    }
}

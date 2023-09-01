package by.wadikk.telegrambot.checker;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface Checker {

    BotApiMethod<?> check(CallbackQuery buttonQuery);
}

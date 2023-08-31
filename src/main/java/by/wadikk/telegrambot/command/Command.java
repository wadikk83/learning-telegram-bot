package by.wadikk.telegrambot.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    BotApiMethod<?> execute(Update update);
}

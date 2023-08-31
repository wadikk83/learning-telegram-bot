package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.service.SendMessageBotService;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Command {

    private final SendMessageBotService service;

    private final String message = "Я не понимаю тебя \uD83D\uDE1F, нажми Помощь чтобы узнать что я понимаю.";

    public UnknownCommand(SendMessageBotService service) {
        this.service = service;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        return service.sendMessage(update.getMessage().getChatId(), message);
    }
}

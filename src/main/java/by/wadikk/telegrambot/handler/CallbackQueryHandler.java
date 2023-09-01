package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.checker.CheckerContainer;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler {

    private final CheckerContainer checkerContainer;

    public CallbackQueryHandler(CheckerContainer checkerContainer) {
        this.checkerContainer = checkerContainer;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {

        String[] data = buttonQuery.getData().split("_");
        return checkerContainer.findChecker(data[1]).check(buttonQuery);
    }
}

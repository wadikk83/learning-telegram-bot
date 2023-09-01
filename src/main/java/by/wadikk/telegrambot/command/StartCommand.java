package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements Command {

    private final ReplyKeyboardMaker replyKeyboardMaker;


    private final String message = "Привет. Я маленький телеграм бот и я еще тоже учусь.\n " +
            "Здесь представлены небольшие задания по различным школьным предметам \n" +
            "Выбери интересующий тебя предмет и используй клавиатуру для ответа";

    public StartCommand(ReplyKeyboardMaker replyKeyboardMaker) {
        this.replyKeyboardMaker = replyKeyboardMaker;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                message);
        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(update.getMessage().getFrom().getId()));
        return sendMessage;
    }
}

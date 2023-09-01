package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnknownCommand implements Command {

    private final String message = "Я не понимаю тебя \n "+
        "Пожалуйста, воспользуйтесь клавиатурой \uD83D\uDC47\n"+
            "или нажми Помощь чтобы узнать что я понимаю.";

    private final ReplyKeyboardMaker replyKeyboardMaker;

    public UnknownCommand(ReplyKeyboardMaker replyKeyboardMaker) {
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

package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MainMenuCommand implements Command {

    private final ReplyKeyboardMaker replyKeyboardMaker;

    private final String message = "Основное меню";

    public MainMenuCommand(ReplyKeyboardMaker replyKeyboardMaker) {
        this.replyKeyboardMaker = replyKeyboardMaker;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(update.getMessage().getFrom().getId()));

        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }
}

package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StatisticsCommand implements Command {

    private final ReplyKeyboardMaker replyKeyboardMaker;

    private final UserService userService;

    private final String message = "За все время использования бота ты ответил на %s вопросов,\n" +
            "из них правильно ответил на %d.\n" +
            "Твой процент успеваемости - %.0f";

    public StatisticsCommand(ReplyKeyboardMaker replyKeyboardMaker, UserService userService) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.userService = userService;
    }


    @Override
    public BotApiMethod<?> execute(Update update) {
        User user = userService.findByUserId(update.getMessage().getFrom().getId()).orElseThrow();
        float percent = (float) ((user.getTotalCorrectAnswers() * 100) / user.getTotalAnswers());
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                String.format(message, user.getTotalAnswers(), user.getTotalCorrectAnswers(), percent));
        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(update.getMessage().getFrom().getId()));
        return sendMessage;
    }
}

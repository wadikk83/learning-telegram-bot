package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminCommand implements Command {

    private final ReplyKeyboardMaker replyKeyboardMaker;

    private final UserService userService;

    private final String message = "За все время использования бота ты ответил на %s вопросов,\n" +
            "из них правильно ответил на %d.\n" +
            "Твой процент успеваемости - %.0f";

    public AdminCommand(ReplyKeyboardMaker replyKeyboardMaker, UserService userService) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.userService = userService;
    }


    @Override
    public BotApiMethod<?> execute(Update update) {
        List<String> collect = userService.findAllUsers().stream().map(user -> user.toString()).collect(Collectors.toList());
        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                collect.toString());
        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(update.getMessage().getFrom().getId()));
        return sendMessage;
    }
}

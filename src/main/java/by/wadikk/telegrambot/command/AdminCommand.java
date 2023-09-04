package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.keyboards.AdminReplyKeyboardMaker;
import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminCommand implements Command {

    private final AdminReplyKeyboardMaker adminReplyKeyboardMaker;

    private final ReplyKeyboardMaker replyKeyboardMaker;

    private final String message = "Панель администратора \n\n";

    private final String errorMessage = "Доступ только администраторам !!! ";

    private final UserService userService;

    public AdminCommand(AdminReplyKeyboardMaker adminReplyKeyboardMaker, ReplyKeyboardMaker replyKeyboardMaker, UserService userService) {
        this.adminReplyKeyboardMaker = adminReplyKeyboardMaker;
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.userService = userService;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());

        //смотрим что пользователь админ
        User user = userService.findByUserId(update.getMessage().getFrom().getId()).orElseThrow();
        if (user.getIsAdmin()) {

            sendMessage.setText(message);
            sendMessage.setReplyMarkup(adminReplyKeyboardMaker.getMainMenuKeyboard(update.getMessage().getFrom().getId()));
        } else {
            sendMessage.setText(errorMessage);
            sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(update.getMessage().getFrom().getId()));
        }

        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        return sendMessage;
    }
}

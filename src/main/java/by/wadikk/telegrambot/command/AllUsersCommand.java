package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.keyboards.AdminReplyKeyboardMaker;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllUsersCommand implements Command {

    private final UserService userService;

    private final AdminReplyKeyboardMaker adminReplyKeyboardMaker;

    public AllUsersCommand(UserService userService,
                           AdminReplyKeyboardMaker adminReplyKeyboardMaker) {
        this.userService = userService;
        this.adminReplyKeyboardMaker = adminReplyKeyboardMaker;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        List<String> allUsers = userService.findAllUsers()
                .stream()
                .map(user -> "id - " + user.getId().toString() + " name - " + user.getName() + "\n")
                .collect(Collectors.toList());

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText(allUsers.toString().replace(",", ""));

        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(adminReplyKeyboardMaker.getMainMenuKeyboard(update.getMessage().getFrom().getId()));
        return sendMessage;
    }
}

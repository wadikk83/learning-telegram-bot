package by.wadikk.telegrambot.checker;

import by.wadikk.telegrambot.service.RussianTaskService;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class RussianChecker implements Checker {

    private final RussianTaskService service;

    private final UserService userService;

    public RussianChecker(RussianTaskService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @Override
    public BotApiMethod<?> check(CallbackQuery buttonQuery) {

        String chatId = buttonQuery.getMessage().getChatId().toString();
        Long userId = buttonQuery.getFrom().getId();
        String[] data = buttonQuery.getData().split("_");
        String answerText;
        String answer = service.getTaskById(Long.parseLong(data[2])).getCorrectAnswer();
        if (answer.equals(data[3])) {
            answerText = ConfirmEnum.randomConfirm().getMessage();
            userService.addAnswer(userId, true);
        } else {
            answerText = MistakeEnum.randomConfirm().getMessage() + "\n\n" +
                    "Правильный ответ - " + answer;
            userService.addAnswer(userId, false);
        }
        return new SendMessage(chatId, answerText);
    }
}

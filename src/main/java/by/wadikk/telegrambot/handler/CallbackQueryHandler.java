package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.model.CallbackDataEnum;
import by.wadikk.telegrambot.model.ConfirmEnum;
import by.wadikk.telegrambot.model.MistakeEnum;
import by.wadikk.telegrambot.service.TaskService;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler {

    private final TaskService taskService;

    private final UserService userService;

    public CallbackQueryHandler(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String data = buttonQuery.getData();

        if (data.contains(CallbackDataEnum.TASK_.name())) {
            return checkTask(buttonQuery);
        }
        return null;
    }

    private SendMessage checkTask(CallbackQuery query) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(query.getMessage().getChatId());
        String[] data = query.getData().split("_");

        String answer = taskService.getTaskById(Long.parseLong(data[1])).getCorrectAnswer();

        if (answer.equals(data[2])) {
            sendMessage.setText(ConfirmEnum.randomConfirm().getMessage());
            userService.addAnswer(query.getFrom().getId(), true);

        } else {
            sendMessage.setText(MistakeEnum.randomConfirm().getMessage() + " \n\n" +
                    "Правильный ответ - " + answer);
            userService.addAnswer(query.getFrom().getId(), false);
        }
        return sendMessage;
    }
}

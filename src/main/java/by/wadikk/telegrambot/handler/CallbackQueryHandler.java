package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.model.CallbackDataEnum;
import by.wadikk.telegrambot.service.TaskService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler {

    private final TaskService taskService;

    public CallbackQueryHandler(TaskService taskService) {
        this.taskService = taskService;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final String chatId = buttonQuery.getMessage().getChatId().toString();

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
            sendMessage.setText("Правильно");
        } else {
            sendMessage.setText("Неправильно");
        }
        return sendMessage;
    }
}

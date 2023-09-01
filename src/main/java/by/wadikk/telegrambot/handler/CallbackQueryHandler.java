package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.command.ButtonNameEnum;
import by.wadikk.telegrambot.model.CallbackDataEnum;
import by.wadikk.telegrambot.model.ConfirmEnum;
import by.wadikk.telegrambot.model.MistakeEnum;
import by.wadikk.telegrambot.service.EnglishTaskService;
import by.wadikk.telegrambot.service.MathTaskService;
import by.wadikk.telegrambot.service.RussianTaskService;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
public class CallbackQueryHandler {

    private final MathTaskService mathTaskService;

    private final RussianTaskService russianTaskService;

    private final EnglishTaskService englishTaskService;

    private final UserService userService;

    public CallbackQueryHandler(MathTaskService taskService,
                                RussianTaskService russianTaskService,
                                EnglishTaskService englishTaskService, UserService userService) {
        this.mathTaskService = taskService;
        this.russianTaskService = russianTaskService;
        this.englishTaskService = englishTaskService;
        this.userService = userService;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        Long userId = buttonQuery.getFrom().getId();
        if (buttonQuery.getData().contains(CallbackDataEnum.TASK_.name())) {
            String[] data = buttonQuery.getData().split("_");

            if (data[1].equals("russian")) {
                return checkRussianTask(data, chatId, userId);
            } else if (data[1].equals("math")) {
                return checkMathTask(data, chatId, userId);
            } else if (data[1].equals("english")) {
                return checkEnglishTask(data, chatId, userId);
            }
        }
        return new SendMessage(buttonQuery.getMessage().getChatId().toString(),
                ButtonNameEnum.NON_COMMAND_MESSAGE.getButtonName());
    }

    private SendMessage checkRussianTask(String[] data, String chatId, Long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        String answer = russianTaskService.getTaskById(Long.parseLong(data[2])).getCorrectAnswer();
        if (answer.equals(data[3])) {
            sendMessage.setText(ConfirmEnum.randomConfirm().getMessage());
            userService.addAnswer(userId, true);
        } else {
            sendMessage.setText(MistakeEnum.randomConfirm().getMessage() + " \n\n" +
                    "Правильный ответ - " + answer);
            userService.addAnswer(userId, false);
        }
        return sendMessage;
    }

    private SendMessage checkMathTask(String[] data, String chatId, Long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        String answer = mathTaskService.getTaskById(Long.parseLong(data[2])).getCorrectAnswer();
        if (answer.equals(data[3])) {
            sendMessage.setText(ConfirmEnum.randomConfirm().getMessage());
            userService.addAnswer(userId, true);
        } else {
            sendMessage.setText(MistakeEnum.randomConfirm().getMessage() + " \n\n" +
                    "Правильный ответ - " + answer);
            userService.addAnswer(userId, false);
        }
        return sendMessage;
    }

    private SendMessage checkEnglishTask(String[] data, String chatId, Long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        String answer = englishTaskService.getTaskById(Long.parseLong(data[2])).getCorrectAnswer();
        if (answer.equals(data[3])) {
            sendMessage.setText(ConfirmEnum.randomConfirm().getMessage());
            userService.addAnswer(userId, true);
        } else {
            sendMessage.setText(MistakeEnum.randomConfirm().getMessage() + " \n\n" +
                    "Correct answer - " + answer);
            userService.addAnswer(userId, false);
        }
        return sendMessage;
    }

}

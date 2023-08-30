package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.entity.Task;
import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.keyboards.InlineKeyboardMaker;
import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import by.wadikk.telegrambot.model.BotMessageEnum;
import by.wadikk.telegrambot.model.ButtonNameEnum;
import by.wadikk.telegrambot.model.CallbackDataEnum;
import by.wadikk.telegrambot.service.TaskService;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageHandler {

    private final ReplyKeyboardMaker replyKeyboardMaker;

    private final InlineKeyboardMaker inlineKeyboardMaker;

    private final TaskService taskService;

    private final UserService userService;

    public MessageHandler(ReplyKeyboardMaker replyKeyboardMaker, InlineKeyboardMaker inlineKeyboardMaker, TaskService taskService, UserService userService) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.taskService = taskService;
        this.userService = userService;
    }

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();

        //валидируем юзера, если новый, то добавляем в базу
        validateUser(message.getFrom());

        String inputText = message.getText();

        if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId, message.getFrom().getId());
        } else if (inputText.equals(ButtonNameEnum.GET_MATHEMATICS_TASKS_BUTTON.getButtonName())) {
            return getMathematicsTaskMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_ENGLISH_TASKS_BUTTON.getButtonName())) {
            return getEnglishTaskMessage(chatId, message.getFrom().getId());
        } else if (inputText.equals(ButtonNameEnum.HELP_BUTTON.getButtonName())) {
            SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
            //включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
            sendMessage.enableMarkdown(true);
            return sendMessage;
        } else {
            return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        }
    }

    private BotApiMethod<?> getEnglishTaskMessage(String chatId, Long userId) {
        SendMessage sendMessage = new SendMessage(chatId, "Данный раздел в разработке. Попробуйте попозже");
        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(userId));
        return sendMessage;
    }

    private BotApiMethod<?> getMathematicsTaskMessage(String chatId) {

        Task randomTask = taskService.getRandomTask();

        SendMessage sendMessage = new SendMessage(chatId, "Задание по математике \n\n" +
                randomTask.getTask());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineTaskButtons(
                CallbackDataEnum.TASK_.name() + randomTask.getId() + "_",
                randomTask.getAnswers()));
        return sendMessage;
    }


    private BotApiMethod<?> getStartMessage(String chatId, Long userId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(userId));
        return sendMessage;
    }

    private void validateUser(org.telegram.telegrambots.meta.api.objects.User fromTelegram) {
        if (userService.findByUserId(fromTelegram.getId()) == null) {
            userService.save(
                    User.builder()
                            .id(fromTelegram.getId())
                            .name(fromTelegram.getUserName())
                            .isAdmin(false)
                            .totalAnswers(0)
                            .totalCorrectAnswers(0)
                            .build());
        }
    }
}

package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.command.CommandContainer;
import by.wadikk.telegrambot.entity.EnglishTask;
import by.wadikk.telegrambot.entity.MathTask;
import by.wadikk.telegrambot.entity.RussianTask;
import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.keyboards.InlineKeyboardMaker;
import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import by.wadikk.telegrambot.model.ButtonNameEnum;
import by.wadikk.telegrambot.model.CallbackDataEnum;
import by.wadikk.telegrambot.service.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.wadikk.telegrambot.model.ButtonNameEnum.NO_COMMAND;

@Component
public class MessageHandler {
    public static String COMMAND_PREFIX = "/";

    private final ReplyKeyboardMaker replyKeyboardMaker;

    private final InlineKeyboardMaker inlineKeyboardMaker;

    private final MathTaskService mathTaskService;

    private final RussianTaskService russianTaskService;

    private final EnglishTaskService englishTaskService;

    private final UserService userService;

    private final CommandContainer commandContainer;

    private final SendMessageBotService sendMessageBotService;

    public MessageHandler(ReplyKeyboardMaker replyKeyboardMaker,
                          InlineKeyboardMaker inlineKeyboardMaker,
                          MathTaskService mathTaskService,
                          RussianTaskService russianTaskService,
                          EnglishTaskService englishTaskService,
                          UserService userService,
                          CommandContainer commandContainer,
                          SendMessageBotService sendMessageBotService) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.mathTaskService = mathTaskService;
        this.russianTaskService = russianTaskService;
        this.englishTaskService = englishTaskService;
        this.userService = userService;
        this.commandContainer = commandContainer;
        this.sendMessageBotService = sendMessageBotService;
    }

    @SneakyThrows
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
            return getEnglishTaskMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.GET_RUSSIAN_TASKS_BUTTON.getButtonName())) {
            return getRussianTaskMessage(chatId);
        } else if (inputText.equals(ButtonNameEnum.HELP_BUTTON.getButtonName())) {
            //todo для теста
            SendMessage sendMessage = new SendMessage(chatId, ButtonNameEnum.HELP_MESSAGE.getButtonName());
            //включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
            sendMessage.enableMarkdown(true);
            return sendMessage;
        } else {
            return new SendMessage(chatId, ButtonNameEnum.NON_COMMAND_MESSAGE.getButtonName());
        }
    }

    private BotApiMethod<?> getMathematicsTaskMessage(String chatId) {

        MathTask randomTask = mathTaskService.getRandomTask().orElse(
                MathTask.builder().build()
        );

        if (randomTask.getTask() == null) {
            return new SendMessage(chatId, "Список заданий пустой");
        }

        SendMessage sendMessage = new SendMessage(chatId, "Задание по математике \n\n" +
                randomTask.getTask());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineTaskButtons(
                CallbackDataEnum.TASK_.name() +
                        "math_" + randomTask.getId() + "_",
                randomTask.getAnswers()));
        return sendMessage;
    }

    private BotApiMethod<?> getRussianTaskMessage(String chatId) {

        RussianTask randomTask = russianTaskService.getRandomTask().orElse(
                RussianTask.builder().build()
        );

        //todo сделать нормальный обработчик
        if (randomTask.getTask() == null) {
            return new SendMessage(chatId, "Список заданий пустой");
        }

        SendMessage sendMessage = new SendMessage(chatId, "Задание по русскому языку \n\n" +
                randomTask.getTask());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineTaskButtons(
                CallbackDataEnum.TASK_.name() +
                        "russian_" + randomTask.getId() + "_",
                randomTask.getAnswers()));
        return sendMessage;
    }

    private BotApiMethod<?> getEnglishTaskMessage(String chatId) {

        EnglishTask randomTask = englishTaskService.getRandomTask().orElse(
                EnglishTask.builder().build()
        );

        //todo сделать нормальный обработчик
        if (randomTask.getTask() == null) {
            return new SendMessage(chatId, "Список заданий пустой");
        }

        SendMessage sendMessage = new SendMessage(chatId, "English task \n\n" +
                randomTask.getTask());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineTaskButtons(
                CallbackDataEnum.TASK_.name() +
                        "english" + randomTask.getId() + "_",
                randomTask.getAnswers()));
        return sendMessage;
    }


    private BotApiMethod<?> getStartMessage(String chatId, Long userId) {
        SendMessage sendMessage = new SendMessage(chatId, ButtonNameEnum.HELP_MESSAGE.getButtonName());
        // включаем поддержку режима разметки, чтобы управлять отображением текста и добавлять эмодзи
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard(userId));
        return sendMessage;
    }

    private void validateUser(org.telegram.telegrambots.meta.api.objects.User fromTelegram) {

        if (!userService.findByUserId(fromTelegram.getId()).isPresent()) {
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

    public BotApiMethod<?> newAnswerMessage(Update update) {
        validateUser(update.getMessage().getFrom());

        String message = update.getMessage().getText().trim();
        String username = update.getMessage().getFrom().getUserName();
        if (message.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = message.split(" ")[0].toLowerCase();
            return commandContainer.findCommand(commandIdentifier, username).execute(update);
        } else {
            return commandContainer.findCommand(NO_COMMAND.getCommandName(), username).execute(update);
        }
    }
}

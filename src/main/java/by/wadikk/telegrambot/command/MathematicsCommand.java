package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.entity.MathTask;
import by.wadikk.telegrambot.keyboards.ReplyKeyboardMaker;
import by.wadikk.telegrambot.model.CallbackDataEnum;
import by.wadikk.telegrambot.service.MathTaskService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MathematicsCommand implements Command {

    private final MathTaskService service;

    private final ReplyKeyboardMaker keyboardMaker;

    private String message = "Задание по математике";

    public MathematicsCommand(MathTaskService service, ReplyKeyboardMaker keyboardMaker) {
        this.service = service;
        this.keyboardMaker = keyboardMaker;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        MathTask randomTask = service.getRandomTask().orElse(
                MathTask.builder().build()
        );

        if (randomTask.getTask() == null) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Список заданий пустой");
        }

        sendService.sendMessage(update.getMessage().getChatId(), "Задание по математике \n\n" +
                randomTask.getTask());


        SendMessage sendMessage = new SendMessage(chatId, "Задание по математике \n\n" +
                randomTask.getTask());
        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineTaskButtons(
                CallbackDataEnum.TASK_.name() +
                        "math_" + randomTask.getId() + "_",
                randomTask.getAnswers()));
        return sendMessage;

    }
}

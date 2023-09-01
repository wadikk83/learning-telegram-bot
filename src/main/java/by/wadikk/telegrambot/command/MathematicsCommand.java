package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.checker.CallbackDataCheckerEnum;
import by.wadikk.telegrambot.entity.MathTask;
import by.wadikk.telegrambot.keyboards.InlineKeyboardMaker;
import by.wadikk.telegrambot.service.MathTaskService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MathematicsCommand implements Command {

    private final MathTaskService service;

    private final InlineKeyboardMaker inlineKeyboardMaker;

    private final String message = "Задание по математике \n\n";

    public MathematicsCommand(MathTaskService service,
                              InlineKeyboardMaker inlineKeyboardMaker) {
        this.service = service;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        MathTask randomTask = service.getRandomTask().orElse(
                MathTask.builder().build()
        );

        if (randomTask.getTask() == null) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Список заданий пустой");
        }

        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                message + randomTask.getTask());

        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineTaskButtons(
                CallbackDataCheckerEnum.TASK_.name() +
                        CallbackDataCheckerEnum.MATH.name() +
                        "_" + randomTask.getId() + "_",
                randomTask.getAnswers()));
        return sendMessage;
    }
}

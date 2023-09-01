package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.entity.RussianTask;
import by.wadikk.telegrambot.keyboards.InlineKeyboardMaker;
import by.wadikk.telegrambot.model.CallbackDataEnum;
import by.wadikk.telegrambot.service.RussianTaskService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class RussianCommand implements Command {
    private final RussianTaskService service;

    private final InlineKeyboardMaker inlineKeyboardMaker;

    private final String message = "Задание по русскому языку \n\n";

    public RussianCommand(RussianTaskService service, InlineKeyboardMaker inlineKeyboardMaker) {
        this.service = service;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        RussianTask randomTask = service.getRandomTask().orElse(
                RussianTask.builder().build()
        );

        //todo
        if (randomTask.getTask() == null) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Список заданий пустой");
        }

        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                message + randomTask.getTask());

        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineTaskButtons(
                CallbackDataEnum.TASK_.name() +
                        "russian_" + randomTask.getId() + "_",
                randomTask.getAnswers()));
        return sendMessage;
    }
}

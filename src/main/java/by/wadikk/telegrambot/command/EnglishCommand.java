package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.checker.CallbackDataCheckerEnum;
import by.wadikk.telegrambot.entity.EnglishTask;
import by.wadikk.telegrambot.keyboards.InlineKeyboardMaker;
import by.wadikk.telegrambot.service.EnglishTaskService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EnglishCommand implements Command {

    private final EnglishTaskService service;

    private final InlineKeyboardMaker inlineKeyboardMaker;

    private final String message = "English task \n\n";

    public EnglishCommand(EnglishTaskService service, InlineKeyboardMaker inlineKeyboardMaker) {
        this.service = service;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        EnglishTask randomTask = service.getRandomTask().orElse(
                EnglishTask.builder().build()
        );

        //todo
        if (randomTask.getTask() == null) {
            return new SendMessage(update.getMessage().getChatId().toString(), "Список заданий пустой");
        }

        SendMessage sendMessage = new SendMessage(update.getMessage().getChatId().toString(),
                message + randomTask.getTask());

        sendMessage.setReplyMarkup(inlineKeyboardMaker.getInlineTaskButtons(
                CallbackDataCheckerEnum.TASK_.name() +
                        CallbackDataCheckerEnum.ENGLISH.name() +
                        "_" + randomTask.getId() + "_",
                randomTask.getAnswers()));
        return sendMessage;
    }
}

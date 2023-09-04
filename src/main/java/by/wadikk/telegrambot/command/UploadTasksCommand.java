package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.utils.UploadService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UploadTasksCommand implements Command {

    private final UploadService uploadFile;

    public UploadTasksCommand(UploadService uploadFile) {
        this.uploadFile = uploadFile;
    }

    @Override
    public BotApiMethod<?> execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();


        try {
            uploadFile.uploadFile(chatId, null);
        } catch (Exception e) {
            return new SendMessage(chatId, "Неожиданная ошибка при попытке получить файл. Сам в шоке");
        }
        return null;
    }
}

package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.command.CommandContainer;
import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class MessageHandler {

    private final UserService userService;

    private final CommandContainer commandContainer;

    public MessageHandler(UserService userService, CommandContainer commandContainer) {
        this.userService = userService;
        this.commandContainer = commandContainer;
    }

    private void validateUser(org.telegram.telegrambots.meta.api.objects.User fromTelegram) {

        if (userService.findByUserId(fromTelegram.getId()).isEmpty()) {
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

    public BotApiMethod<?> answerMessage(Update update) {
        validateUser(update.getMessage().getFrom());

        String message = update.getMessage().getText().trim();
        //ищем команду в контейнере
        return commandContainer.findCommand(message).execute(update);
    }
}

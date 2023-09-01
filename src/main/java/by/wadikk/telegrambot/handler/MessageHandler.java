package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.command.Command;
import by.wadikk.telegrambot.command.CommandContainer;
import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import static by.wadikk.telegrambot.command.ButtonNameEnum.NO_COMMAND;

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

    public BotApiMethod<?> newAnswerMessage(Update update) {
        validateUser(update.getMessage().getFrom());

        String message = update.getMessage().getText().trim();
        String username = update.getMessage().getFrom().getUserName();

        //ищем команду в контейнере
        Command command = commandContainer.findCommand(message, username);

        if (command != null) {
            return command.execute(update);
        } else {
            return commandContainer.findCommand(NO_COMMAND.getCommandName(), username).execute(update);
        }
    }
}

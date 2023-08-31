package by.wadikk.telegrambot.command;

import by.wadikk.telegrambot.service.SendMessageBotService;
import com.google.common.collect.ImmutableMap;

import static by.wadikk.telegrambot.model.ButtonNameEnum.*;

public class CommandContainer {

    private SendMessageBotService sendService;

    private final MathematicsCommand mathematicsCommand;

    private final ImmutableMap<String, Command> commandMap;

    private final Command unknownCommand;

    public CommandContainer(MathematicsCommand mathematicsCommand) {
        this.mathematicsCommand = mathematicsCommand;

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START_MESSAGE.getCommandName(), new StartCommand())
                .put(HELP_MESSAGE.getCommandName(), new HelpCommand())
                .put(MATHEMATICS.getCommandName(), mathematicsCommand)
                .build();
        unknownCommand = new UnknownCommand(sendService);
    }

    public Command findCommand(String commandIdentifier, String username) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}

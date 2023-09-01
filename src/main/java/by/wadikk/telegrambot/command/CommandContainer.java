package by.wadikk.telegrambot.command;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import static by.wadikk.telegrambot.command.ButtonNameEnum.*;

@Component
public class CommandContainer {

    private final StartCommand startCommand;
    private final MathematicsCommand mathematicsCommand;
    private final EnglishCommand englishCommand;
    private final RussianCommand russianCommand;
    private final HelpCommand helpCommand;
    private final StatisticsCommand statisticsCommand;
    private final AdminCommand adminCommand;
    private final UnknownCommand unknownCommand;
    private final ImmutableMap<String, Command> commandMap;

    public CommandContainer(StartCommand startCommand,
                            MathematicsCommand mathematicsCommand,
                            EnglishCommand englishCommand,
                            RussianCommand russianCommand,
                            HelpCommand helpCommand,
                            StatisticsCommand statisticsCommand,
                            AdminCommand adminCommand, UnknownCommand unknownCommand) {
        this.startCommand = startCommand;
        this.mathematicsCommand = mathematicsCommand;
        this.englishCommand = englishCommand;
        this.russianCommand = russianCommand;
        this.helpCommand = helpCommand;
        this.statisticsCommand = statisticsCommand;
        this.adminCommand = adminCommand;
        this.unknownCommand = unknownCommand;

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START_MESSAGE.getCommandName(), startCommand)
                .put(START_MESSAGE.getButtonName(), startCommand)
                .put(HELP.getCommandName(), helpCommand)
                .put(HELP.getButtonName(), helpCommand)
                .put(MATHEMATICS.getCommandName(), mathematicsCommand)
                .put(MATHEMATICS.getButtonName(), mathematicsCommand)
                .put(RUSSIAN.getCommandName(), russianCommand)
                .put(RUSSIAN.getButtonName(), russianCommand)
                .put(ENGLISH.getCommandName(), englishCommand)
                .put(ENGLISH.getButtonName(), englishCommand)
                .put(STATISTICS.getCommandName(), statisticsCommand)
                .put(STATISTICS.getButtonName(), statisticsCommand)
                .put(ADMIN.getCommandName(), adminCommand)
                .put(ADMIN.getButtonName(), adminCommand)
                .build();
    }

    public Command findCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}

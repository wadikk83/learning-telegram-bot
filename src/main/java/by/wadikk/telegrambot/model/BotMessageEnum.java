package by.wadikk.telegrambot.model;

public enum BotMessageEnum {

    HELP_MESSAGE("Привет, я бот и я еще учусь. Здесь представлены задания для решения задач"),

    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь клавиатурой \uD83D\uDC47");

    private final String message;

    BotMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

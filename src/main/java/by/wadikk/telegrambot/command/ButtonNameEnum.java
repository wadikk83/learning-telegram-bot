package by.wadikk.telegrambot.command;

import lombok.Getter;

public enum ButtonNameEnum {

    START_MESSAGE("Старт", "/start"),
    NON_COMMAND_MESSAGE("", "Пожалуйста, воспользуйтесь клавиатурой \uD83D\uDC47"),
    GET_TASKS_BUTTON("buttonName", "Создать файл с заданиями"),
    UPLOAD_TASKS_BUTTON("buttonName", "Загрузить файл с заданиями"),
    HELP("Помощь", "/help"),
    MATHEMATICS("Математика", "/mathematics"),
    ENGLISH("Английский язык", "/english"),
    RUSSIAN("Русский язык", "/russian"),
    ADMIN("Админ", "/admin"),
    STATISTICS("Статистика", "/stat"),
    NO_COMMAND("nocommand", "nocommand");


    @Getter
    private final String buttonName;

    @Getter
    private final String commandName;

    ButtonNameEnum(String buttonName, String commandName) {
        this.buttonName = buttonName;
        this.commandName = commandName;
    }
}

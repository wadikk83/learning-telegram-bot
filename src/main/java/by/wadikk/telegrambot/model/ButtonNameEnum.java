package by.wadikk.telegrambot.model;

import lombok.Getter;

public enum ButtonNameEnum {

    START_MESSAGE("Старт", "/start"),
    HELP_MESSAGE("Помощь", "Привет, я бот и я еще учусь. Здесь представлены небольшие задания по различным предметам \n" +
            "Выбери интересующий тебя предмет и используй клавиатуру для ответа"),
    NON_COMMAND_MESSAGE("", "Пожалуйста, воспользуйтесь клавиатурой \uD83D\uDC47"),
    GET_TASKS_BUTTON("buttonName", "Создать файл с заданиями"),
    UPLOAD_TASKS_BUTTON("buttonName", "Загрузить файл с заданиями"),
    HELP_BUTTON("buttonName", "Помощь"),
    MATHEMATICS("Математика", "/mathematics"),
    GET_ENGLISH_TASKS_BUTTON("buttonName", "Английский язык"),
    GET_RUSSIAN_TASKS_BUTTON("buttonName", "Русский язык"),
    ADMIN_BUTTON("Админ", "/admin"),
    STATISTICS("Статистика", "/stat"),
    NO_COMMAND("buttonName", "nocommand");


    @Getter
    private final String buttonName;

    @Getter
    private final String commandName;

    ButtonNameEnum(String buttonName, String commandName) {
        this.buttonName = buttonName;
        this.commandName = commandName;
    }
}

package by.wadikk.telegrambot.model;

public enum ButtonNameEnum {

    HELP_MESSAGE("Привет, я бот и я еще учусь. Здесь представлены небольшие задания по различным предметам \n"+
            "Выбери интересующий тебя предмет и используй клавиатуру для ответа"),
    NON_COMMAND_MESSAGE("Пожалуйста, воспользуйтесь клавиатурой \uD83D\uDC47"),
    GET_TASKS_BUTTON("Создать файл с заданиями"),
    UPLOAD_TASKS_BUTTON("Загрузить файл с заданиями"),
    HELP_BUTTON("Помощь"),
    GET_MATHEMATICS_TASKS_BUTTON("Математика"),
    GET_ENGLISH_TASKS_BUTTON("Английский язык"),
    GET_RUSSIAN_TASKS_BUTTON("Русский язык"),
    ADMIN_BUTTON("Админ"),
    STATISTICS("Статистика");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}

package by.wadikk.telegrambot.model;

public enum ButtonNameEnum {
    GET_TASKS_BUTTON("Создать файл с заданиями"),
    UPLOAD_TASKS_BUTTON("Загрузить файл с заданиями"),
    HELP_BUTTON("Помощь"),

    GET_MATHEMATICS_TASKS_BUTTON("Математика"),

    GET_ENGLISH_TASKS_BUTTON("Английский язык"),

    ADMIN_BUTTON("Админ");

    private final String buttonName;

    ButtonNameEnum(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getButtonName() {
        return buttonName;
    }
}

package by.wadikk.telegrambot.model;

import java.util.Random;

public enum MistakeEnum {

    MISTAKE1("Неправильно \uD83D\uDE41"),
    MISTAKE2("Ты ошибаешься \uD83E\uDD28"),
    MISTAKE3("я не согласен \uD83D\uDE27");

    private String message;

    private static final Random PRNG = new Random();

    MistakeEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static MistakeEnum randomConfirm() {
        MistakeEnum[] messages = values();
        return messages[PRNG.nextInt(messages.length)];
    }
}

package by.wadikk.telegrambot.model;

import java.util.Random;

public enum ConfirmEnum {

    CONFIRM1("Молодец \uD83E\uDDE8"),
    CONFIRM2("Так держать \uD83D\uDE0E"),
    CONFIRM3("Умница \uD83D\uDC4D");

    private static final Random PRNG = new Random();
    private String message;

    ConfirmEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static ConfirmEnum randomConfirm() {
        ConfirmEnum[] messages = values();
        return messages[PRNG.nextInt(messages.length)];
    }
}

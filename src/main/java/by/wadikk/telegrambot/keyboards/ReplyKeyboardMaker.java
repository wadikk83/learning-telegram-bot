package by.wadikk.telegrambot.keyboards;


import by.wadikk.telegrambot.config.TelegramConfig;
import by.wadikk.telegrambot.model.ButtonNameEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {

    private final TelegramConfig telegramConfig;

    public ReplyKeyboardMaker(TelegramConfig telegramConfig) {
        this.telegramConfig = telegramConfig;
    }

    /**
     * Основная клавиатура
     */
    public ReplyKeyboardMarkup getMainMenuKeyboard(Long userId) {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNameEnum.GET_MATHEMATICS_TASKS_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.GET_ENGLISH_TASKS_BUTTON.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.GET_RUSSIAN_TASKS_BUTTON.getButtonName()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(ButtonNameEnum.HELP_BUTTON.getButtonName()));
        if (userId.longValue() == telegramConfig.getAdminId().longValue()) {
            row2.add(new KeyboardButton(ButtonNameEnum.ADMIN_BUTTON.getButtonName()));
        }


        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboard);

        // Опционально. Этот параметр нужен, чтобы показывать клавиатуру только определённым пользователям.
        // Цели: 1) пользователи, которые были @упомянуты в поле text объекта Message;
        // 2) если сообщения бота является ответом (содержит поле reply_to_message_id), авторы этого сообщения.
        // Пример: Пользователь отправляет запрос на смену языка бота.
        // Бот отправляет клавиатуру со списком языков, видимую только этому пользователю.
        replyKeyboardMarkup.setSelective(true);

        //Опционально. Указывает клиенту подогнать высоту клавиатуры под количество кнопок (сделать её меньше, если кнопок мало).
        // По умолчанию False, то есть клавиатура всегда такого же размера, как и стандартная клавиатура устройства.
        replyKeyboardMarkup.setResizeKeyboard(true);

        // Опционально. Указывает клиенту скрыть клавиатуру после использования (после нажатия на кнопку).
        // Её по-прежнему можно будет открыть через иконку в поле ввода сообщения. По умолчанию False.
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}

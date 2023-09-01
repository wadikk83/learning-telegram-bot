package by.wadikk.telegrambot.keyboards;


import by.wadikk.telegrambot.command.ButtonNameEnum;
import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboardMaker {

    private final UserService userService;

    public ReplyKeyboardMaker(UserService userService) {
        this.userService = userService;
    }

    /**
     * Основная клавиатура
     */
    public ReplyKeyboardMarkup getMainMenuKeyboard(Long userId) {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(ButtonNameEnum.MATHEMATICS.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.ENGLISH.getButtonName()));
        row1.add(new KeyboardButton(ButtonNameEnum.RUSSIAN.getButtonName()));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(ButtonNameEnum.HELP.getButtonName()));
        row2.add(new KeyboardButton(ButtonNameEnum.STATISTICS.getButtonName()));

        //если пользователь админ, то добавляем ему кнопку
        User user = userService.findByUserId(userId).orElseThrow();
        if (user.getIsAdmin()) {
            row2.add(new KeyboardButton(ButtonNameEnum.ADMIN.getButtonName()));
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

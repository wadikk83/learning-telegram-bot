package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.service.MenuService;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Value("${telegram.adminId}")
    private int admin_id;

    private final UserService userService;

    public MenuServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SendMessage getMainMenuMessage(long chatId, String textMessage, long userId) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard(userId);
        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    //Main menu
    @Override
    public ReplyKeyboardMarkup getMainMenuKeyboard(long userId) {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);//подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false);//скрываем после использования
        User user = userService.findByUserId(userId).get();
        String text = user.isOn() ? "Отключить напоминания" : "Включить напоминания";

        //Создаем список с рядами кнопок
        List<KeyboardRow> keyboard = new ArrayList<>();

        //Создаем один ряд кнопок и добавляем его в список
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Создать напоминание"));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("Мои напоминания"));

        KeyboardRow row3 = new KeyboardRow();
        row3.add((new KeyboardButton(text)));

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);
        if (userId == admin_id) {
            KeyboardRow row4 = new KeyboardRow();
            row4.add(new KeyboardButton("All users"));
            row4.add(new KeyboardButton("All events"));
            keyboard.add(row4);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    @Override
    public SendMessage createMessageWithKeyboard(long chatId, String textMessage, ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);// возможность разметки
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }

    @Override
    public InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonDel = new InlineKeyboardButton();
        buttonDel.setText("Удалить");
        InlineKeyboardButton buttonEdit = new InlineKeyboardButton();
        buttonEdit.setText("Редактировать");
        InlineKeyboardButton buttonHour = new InlineKeyboardButton();
        buttonHour.setText("Изенить часовой пояс");

        buttonDel.setCallbackData("buttonDel");
        buttonEdit.setCallbackData("buttonEdit");
        buttonHour.setCallbackData("buttonHour");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonDel);
        keyboardButtonsRow1.add(buttonEdit);
        keyboardButtonsRow2.add(buttonHour);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public ReplyKeyboard getInlineMessageButtonsForEnterDate() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonOneTime = new InlineKeyboardButton();
        buttonOneTime.setText("Единоразово");
        InlineKeyboardButton buttonEveryDay = new InlineKeyboardButton();
        buttonEveryDay.setText("Ежедневно");
        InlineKeyboardButton buttonOneTimeMonth = new InlineKeyboardButton();
        buttonOneTimeMonth.setText("Раз в месяц");
        InlineKeyboardButton buttonOneTimeYear = new InlineKeyboardButton();
        buttonOneTimeYear.setText("Раз в год");

        buttonOneTime.setCallbackData("buttonOneTime");
        buttonEveryDay.setCallbackData("buttonEveryDay");
        buttonOneTimeMonth.setCallbackData("buttonOneTimeMonth");
        buttonOneTimeYear.setCallbackData("buttonOneTimeYear");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonOneTime);
        keyboardButtonsRow1.add(buttonEveryDay);
        keyboardButtonsRow1.add(buttonOneTimeMonth);
        keyboardButtonsRow1.add(buttonOneTimeYear);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public ReplyKeyboard getInlineMessageForEdit() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonDate = new InlineKeyboardButton();
        buttonDate.setText("Изменить дату");
        InlineKeyboardButton buttonDescription = new InlineKeyboardButton();
        buttonDescription.setText("Изменить описание");
        InlineKeyboardButton buttonFreq = new InlineKeyboardButton();
        buttonFreq.setText("Изменить интервал");

        buttonDate.setCallbackData("buttonDate");
        buttonDescription.setCallbackData("buttonDescription");
        buttonFreq.setCallbackData("buttonFreq");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();


        keyboardButtonsRow1.add(buttonDate);
        keyboardButtonsRow2.add(buttonDescription);
        keyboardButtonsRow3.add(buttonFreq);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    @Override
    public ReplyKeyboard getInlineMessageButtonsAllUser() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonDelUser = new InlineKeyboardButton();
        buttonDelUser.setText("Del user");

        buttonDelUser.setCallbackData("buttonDelUser");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonDelUser);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
}

package by.wadikk.telegrambot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface MenuService {

    SendMessage getMainMenuMessage(final long chatId,
                                   final String textMessage,
                                   final long userId);

    ReplyKeyboardMarkup getMainMenuKeyboard(long userId);

    SendMessage createMessageWithKeyboard(final long chatId,
                                          String textMessage,
                                          final ReplyKeyboardMarkup replyKeyboardMarkup);

    InlineKeyboardMarkup getInlineMessageButtons();


    ReplyKeyboard getInlineMessageButtonsForEnterDate();

    ReplyKeyboard getInlineMessageForEdit();

    ReplyKeyboard getInlineMessageButtonsAllUser();
}

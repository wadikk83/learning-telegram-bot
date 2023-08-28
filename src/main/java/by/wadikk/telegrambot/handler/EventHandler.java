package by.wadikk.telegrambot.handler;

import by.wadikk.telegrambot.entity.Event;
import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.model.BotState;
import by.wadikk.telegrambot.model.EventCash;
import by.wadikk.telegrambot.model.EventFreq;
import by.wadikk.telegrambot.service.EventService;
import by.wadikk.telegrambot.service.MenuService;
import by.wadikk.telegrambot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventHandler {

    @Value("${telegram.adminId}")
    private int admin_id;

    private final BotStateCash botStateCash;
    private final UserService userService;

    private final EventService eventService;

    private final MenuService menuService;

    private final EventCash eventCash;


    public EventHandler(BotStateCash botStateCash, UserService userService, EventService eventService, MenuService menuService, EventCash eventCash) {
        this.botStateCash = botStateCash;
        this.userService = userService;
        this.eventService = eventService;
        this.menuService = menuService;
        this.eventCash = eventCash;
    }

    public SendMessage saveNewUser(Message message, SendMessage sendMessage) {
        User newUser = User.builder()
                .id(message.getFrom().getId())
                .name(message.getFrom().getUserName())
                .on(true)
                .build();
        userService.save(newUser);
        sendMessage.setText("В первый сеанс необходимо ввести местное время в формате HH, например, " +
                "если сейчас 21:45, то введите 21, это необходимо для корректного оповещения в соответствии с вашим часовым поясом.");
        botStateCash.saveBotState(newUser.getId(), BotState.ENTERTIME);
        return sendMessage;
    }

    public BotApiMethod<?> enterLocalTimeUser(Message message) {
        Long userId = message.getFrom().getId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        Date nowHour = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowHour);
        int num;
        try {
            num = Integer.parseInt(message.getText());
        } catch (NumberFormatException exception) {
            sendMessage.setText("Введенные символы не число, повторите ввод");
            return sendMessage;
        }

        if (num < 0 || num > 24) {
            sendMessage.setText("Вы ввели неверное время, повторите.");
            return sendMessage;
        }

        Date userHour;
        try {
            userHour = simpleDateFormat.parse(message.getText());
        } catch (ParseException e) {
            sendMessage.setText("Вы ввели неверное время, повторите.");
            return sendMessage;
        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(userHour);

        int serverHour = calendar.get(Calendar.HOUR_OF_DAY);
        int clientHour = calendar1.get(Calendar.HOUR_OF_DAY);

        // calculate the time zone
        int timeZone = clientHour - serverHour;

        sendMessage.setText("Ваш часовой пояс: " + "+" + timeZone);
        User user = userService.findByUserId(userId).get();
        user.setTimeZone(timeZone);
        userService.save(user);

        //time zone is set, reset state
        botStateCash.saveBotState(userId, BotState.START);
        return sendMessage;
    }

    public BotApiMethod<?> myEventHandler(Long userId) {
        List<Event> events = eventService.findByUserId(userId);
        return eventListBuilder(userId, events);
    }

    public BotApiMethod<?> eventListBuilder(long userId, List<Event> list) {
        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(userId);
        StringBuilder builder = new StringBuilder();
        if (list.isEmpty()) {
            replyMessage.setText("Уведомления отсутствуют!");
            return replyMessage;
        }
        for (Event event : list) {
            builder.append(buildEvent(event));
        }
        replyMessage.setText(builder.toString());
        replyMessage.setReplyMarkup(menuService.getInlineMessageButtons());
        return replyMessage;
    }

    private StringBuilder buildEvent(Event event) {
//        StringBuilder builder = new StringBuilder();
//        //long eventId = event.getEventId();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
//
//        //Date date = event.getDate();
//        //String dateFormat = simpleDateFormat.format(date);
//
//        //String description = event.getDescription();
//        EventFreq freq = event.getFreq();
//        String freqEvent;
//        switch (freq.name()) {
//            case ("TIME"):
//                freqEvent = "Единоразово";
//                break;
//            case ("EVERYDAY"):
//                freqEvent = "Ежедневно";
//                break;
//            case ("MONTH"):
//                freqEvent = "Один раз в месяц";
//                break;
//            case ("YEAR"):
//                freqEvent = "Один раз в год";
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + freq.name());
//        }
//        builder.append("eventId").append(". ").append("dateFormat").append(": ")
//                .append(description).append(": ").append(freqEvent).append("\n");
        return new StringBuilder().append("Event not work");
    }

    public BotApiMethod<?> enterDescriptionHandler(Message message, long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        String description = message.getText();
        if (description.isEmpty() || description.length() < 4 || description.length() > 200) {
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText("Описание должно быть минимум 4 символа, но не более 200");
            return sendMessage;
        }
        //switch th state to enter the date
        botStateCash.saveBotState(userId, BotState.ENTERDATE);
        //get the previous set of event from the cash
        Event event = eventCash.getEventMap().get(userId);
        event.setDescription(description);
        //save to cache
        eventCash.saveEventCash(userId, event);
        sendMessage.setText("Введите дату предстоящего события в формате DD.MM.YYYY HH:MM, например - 02.06.2021 21:24, либо 02.06.2021");
        return sendMessage;
    }

    public SendMessage saveEvent(EventFreq freq, long userId, long chatId) {
        Event event = eventCash.getEventMap().get(userId);
        event.setFreq(freq);
        event.setUser(userService.findByUserId(userId).get());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        eventService.save(event);
        //reset cache
        eventCash.saveEventCash(userId, new Event());
        sendMessage.setText("Напоминание успешно сохранено");
        //reset cache
        botStateCash.saveBotState(userId, BotState.START);
        return sendMessage;
    }

    public SendMessage editEvent(long chatId, long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        //get event from cache
        Event event = eventCash.getEventMap().get(userId);
        //in case something went wrong
        if (event.getEventId() == 0) {
            sendMessage.setText("Не удалось сохранить пользователя, нарушена последовательность действий");
            return sendMessage;
        }
        eventService.save(event);
        sendMessage.setText("Изменение сохранено");
        //reset cache
        eventCash.saveEventCash(userId, new Event());
        return sendMessage;
    }

    public BotApiMethod<?> removeEventHandler(Message message, long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        Event eventRes;
        try {
            //get event from the database
            eventRes = enterNumberEvent(message.getText(), userId);
        } catch (NumberFormatException e) {
            sendMessage.setText("Введенная строка не является числом, попробуйте снова!");
            return sendMessage;
        }
        if (eventRes == null) {
            sendMessage.setText("Введенное число отсутсвует в списке, попробуйте снова!");
            return sendMessage;
        }

        eventService.remove(eventRes);
        //reset state
        botStateCash.saveBotState(userId, BotState.START);
        sendMessage.setText("Удаление прошло успешно");
        return sendMessage;
    }

    private Event enterNumberEvent(String message, long userId) throws NumberFormatException, NullPointerException, EntityNotFoundException {
        List<Event> list;
        if (userId == admin_id) {
            // =))
            list = eventService.findAllEvent();
        } else {
            // =((
            list = eventService.findByUserId(userId);
        }

        int i = Integer.parseInt(message);

        return list.stream().filter(event -> event.getEventId() == i).findFirst().orElseThrow(null);
    }

    public BotApiMethod<?> enterDateHandler(Message message, long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        Date date;
        try {
            date = parseDate(message.getText());
        } catch (ParseException e) {
            sendMessage.setText("Не удается распознать указанную дату и время, попробуйте еще раз");
            return sendMessage;
        }
        //get data of the previous set
        Event event = eventCash.getEventMap().get(userId);
        event.setDate(date);
        //save data to cache
        eventCash.saveEventCash(userId, event);
        sendMessage.setText("Выберите период повторения(Единоразово(сработает один раз и удалится), " +
                "Ежедневно в указанный час, " +
                "1 раз в месяц в указанную дату, 1 раз в год в указанное число)");
        //show to user menu to select the frequency
        sendMessage.setReplyMarkup(menuService.getInlineMessageButtonsForEnterDate());
        return sendMessage;
    }

    private Date parseDate(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return simpleDateFormat.parse(s);
    }

    public BotApiMethod<?> editHandler(Message message, long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        Event eventRes;
        try {
            //awaiting entered eventId, get event from base
            eventRes = enterNumberEvent(message.getText(), userId);
        } catch (NumberFormatException e) {
            sendMessage.setText("Введенная строка не является числом, попробуйте снова!");
            return sendMessage;
        }
        if (eventRes == null) {
            sendMessage.setText("Введенное число отсутсвует в списке, попробуйте снова!");
            return sendMessage;
        }
        //the received event is saved in the cache
        eventCash.saveEventCash(userId, eventRes);
        //the received event show to user
        StringBuilder builder = buildEvent(eventRes);
        sendMessage.setText(builder.toString());
        //show to user menu for edit event
        sendMessage.setReplyMarkup(menuService.getInlineMessageForEdit());
        return sendMessage;
    }

    public BotApiMethod<?> editDescription(Message message) {
        String description = message.getText();
        long userId = message.getFrom().getId();
        //should be not empty, less then 4, no more 200
        if (description.isEmpty() || description.length() < 4 || description.length() > 200) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText("Описание должно быть минимум 4 символа, но не более 200");
            return sendMessage;
        }
        //get data of the previous set
        Event event = eventCash.getEventMap().get(userId);
        event.setDescription(description);
        //save to cash
        eventCash.saveEventCash(userId, event);
        //event input is expected to complete, changes must be saved
        return editEvent(message.getChatId(), userId);
    }

    public BotApiMethod<?> editDate(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        long userId = message.getFrom().getId();
        Date date;
        try {
            date = parseDate(message.getText());
        } catch (ParseException e) {
            sendMessage.setText("Не удается распознать указанную дату и время, попробуйте еще раз");
            return sendMessage;
        }
        //get data of the previous set
        Event event = eventCash.getEventMap().get(userId);
        event.setDate(date);
        eventCash.saveEventCash(userId, event);
        //event input is expected to complete, changes must be saved
        return editEvent(message.getChatId(), userId);
    }

    public BotApiMethod<?> allEvents(long userId) {
        List<Event> list = eventService.findAllEvent();
        botStateCash.saveBotState(userId, BotState.START);
        return eventListBuilder(userId, list);
    }

    public BotApiMethod<?> allUsers(long userId) {
        SendMessage replyMessage = new SendMessage();
        replyMessage.setChatId(String.valueOf(userId));
        StringBuilder builder = new StringBuilder();
        List<User> list = userService.findAllUsers();
        for (User user : list) {
            builder.append(buildUser(user));
        }
        replyMessage.setText(builder.toString());
        replyMessage.setReplyMarkup(menuService.getInlineMessageButtonsAllUser());
        botStateCash.saveBotState(userId, BotState.START);
        return replyMessage;
    }

    private StringBuilder buildUser(User user) {
        StringBuilder builder = new StringBuilder();
        long userId = user.getId();
        String name = user.getName();
        builder.append(userId).append(". ").append(name).append("\n");
        return builder;
    }

    public BotApiMethod<?> onEvent(Message message) {
        User user = userService.findByUserId(message.getFrom().getId()).get();

        boolean on = user.isOn();
        on = !on;
        user.setOn(on);
        userService.save(user);
        botStateCash.saveBotState(message.getFrom().getId(), BotState.START);
        return menuService.getMainMenuMessage(message.getChatId(),
                "Изменения сохранены", message.getFrom().getId());
    }

    public BotApiMethod<?> removeUserHandler(Message message, long userId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        User user;
        try {
            long i = Long.parseLong(message.getText());
            user = userService.findByUserId(i).get();
        } catch (NumberFormatException e) {
            sendMessage.setText("Введенная строка не является числом, попробуйте снова!");
            return sendMessage;
        }
        if (user == null) {
            sendMessage.setText("Введенное число отсутсвует в списке, попробуйте снова!");
            return sendMessage;
        }

        userService.removeUser(user);
        botStateCash.saveBotState(userId, BotState.START);
        sendMessage.setText("Удаление прошло успешно");
        return sendMessage;
    }
}

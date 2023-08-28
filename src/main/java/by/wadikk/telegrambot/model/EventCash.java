package by.wadikk.telegrambot.model;

import by.wadikk.telegrambot.entity.Event;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Setter
@Getter
public class EventCash {
    private final Map<Long, Event> eventMap = new HashMap<>();

    public void saveEventCash(long userId, Event event) {
        eventMap.put(userId, event);
    }
}

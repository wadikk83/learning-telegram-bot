package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<Event> findByUserId(long userId);

    List<Event> findAllEvent();

    Optional<Event> findByEventId(long eventId);

    void remove(Event event);

    void save(Event event);

}

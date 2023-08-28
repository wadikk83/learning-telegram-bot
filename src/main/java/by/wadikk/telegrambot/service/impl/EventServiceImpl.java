package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.entity.Event;
import by.wadikk.telegrambot.entity.User;
import by.wadikk.telegrambot.repository.EventRepository;
import by.wadikk.telegrambot.repository.UserRepository;
import by.wadikk.telegrambot.service.EventService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Event> findByUserId(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get().getEvents();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Event> findAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findByEventId(long eventId) {
        return eventRepository.findById(eventId);
    }

    @Override
    public void remove(Event event) {
        eventRepository.delete(event);
    }

    @Override
    public void save(Event event) {
        eventRepository.save(event);
    }
}

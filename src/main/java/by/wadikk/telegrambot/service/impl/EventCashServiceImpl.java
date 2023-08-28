package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.entity.EventCashEntity;
import by.wadikk.telegrambot.repository.EventCashRepository;
import by.wadikk.telegrambot.service.EventCashService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventCashServiceImpl implements EventCashService {

    private final EventCashRepository eventCashRepository;

    public EventCashServiceImpl(EventCashRepository eventCashRepository) {
        this.eventCashRepository = eventCashRepository;
    }

    @Override
    public List<EventCashEntity> findAllEventCash() {
        return eventCashRepository.findAll();
    }

    @Override
    public void save(EventCashEntity eventCashEntity) {
        eventCashRepository.save(eventCashEntity);
    }

    @Override
    public void delete(long id) {
        eventCashRepository.deleteById(id);
    }
}

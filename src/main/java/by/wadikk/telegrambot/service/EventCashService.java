package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.EventCashEntity;

import java.util.List;

public interface EventCashService {

    List<EventCashEntity> findAllEventCash();

    void save(EventCashEntity eventCashEntity);

    void delete(long id);
}

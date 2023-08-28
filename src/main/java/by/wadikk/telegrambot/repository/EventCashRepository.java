package by.wadikk.telegrambot.repository;

import by.wadikk.telegrambot.entity.EventCashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventCashRepository extends JpaRepository<EventCashEntity, Long> {

    Optional<EventCashEntity> findById(Long id);
}

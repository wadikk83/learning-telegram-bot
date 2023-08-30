package by.wadikk.telegrambot.repository;

import by.wadikk.telegrambot.entity.EnglishTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishTaskRepository extends JpaRepository<EnglishTask, Long> {
}

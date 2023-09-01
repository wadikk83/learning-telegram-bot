package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.EnglishTask;

import java.util.List;
import java.util.Optional;

public interface EnglishTaskService {
    void addTask(EnglishTask task);

    Optional<EnglishTask> getRandomTask();

    List<EnglishTask> getAllTasks();

    EnglishTask getTaskById(Long id);
}

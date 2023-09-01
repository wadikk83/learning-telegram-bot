package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.MathTask;

import java.util.List;
import java.util.Optional;

public interface MathTaskService {
    void addTask(MathTask task);

    Optional<MathTask> getRandomTask();

    List<MathTask> getAllTasks();

    MathTask getTaskById(Long id);

}

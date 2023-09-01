package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.RussianTask;

import java.util.List;
import java.util.Optional;

public interface RussianTaskService {

    void addTask(RussianTask task);

    Optional<RussianTask> getRandomTask();

    List<RussianTask> getAllTasks();

    RussianTask getTaskById(Long id);
}

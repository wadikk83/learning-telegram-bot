package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.MathTask;

import java.util.List;

public interface MathTaskService {
    void addTask(MathTask task);

    MathTask getRandomTask();

    List<MathTask> getAllTasks();

    MathTask getTaskById(Long id);

}

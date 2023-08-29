package by.wadikk.telegrambot.service;

import by.wadikk.telegrambot.entity.Task;

import java.util.List;

public interface TaskService {

    void addTask(Task task);

    Task getRandomTask();

    List<Task> getAllTasks();

    Task getTaskById(Long id);
}

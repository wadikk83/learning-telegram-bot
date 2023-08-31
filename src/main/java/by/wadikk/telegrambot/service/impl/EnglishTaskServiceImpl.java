package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.entity.EnglishTask;
import by.wadikk.telegrambot.repository.EnglishTaskRepository;
import by.wadikk.telegrambot.service.EnglishTaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnglishTaskServiceImpl implements EnglishTaskService {

    private final EnglishTaskRepository repository;

    public EnglishTaskServiceImpl(EnglishTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addTask(EnglishTask task) {
        repository.save(task);
    }

    @Override
    public Optional<EnglishTask> getRandomTask() {

        return Optional.ofNullable(repository.getRandomTask());
    }

    @Override
    public List<EnglishTask> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public EnglishTask getTaskById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}

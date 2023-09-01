package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.entity.RussianTask;
import by.wadikk.telegrambot.repository.RussianTaskRepository;
import by.wadikk.telegrambot.service.RussianTaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RussianTaskServiceImpl implements RussianTaskService {
    private final RussianTaskRepository repository;

    public RussianTaskServiceImpl(RussianTaskRepository repository) {
        this.repository = repository;
    }


    @Override
    public void addTask(RussianTask task) {
        repository.save(task);
    }

    @Override
    public Optional<RussianTask> getRandomTask() {
        return Optional.ofNullable(repository.getRandomTask());
    }

    @Override
    public List<RussianTask> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public RussianTask getTaskById(Long id) {
        return repository.findById(id).orElseThrow();
    }

}

package by.wadikk.telegrambot.service.impl;

import by.wadikk.telegrambot.entity.MathTask;
import by.wadikk.telegrambot.repository.MathTaskRepository;
import by.wadikk.telegrambot.service.MathTaskService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MathTaskServiceImpl implements MathTaskService {

    private final MathTaskRepository repository;

    public MathTaskServiceImpl(MathTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addTask(MathTask task) {
        repository.save(task);
    }

    @Override
    public MathTask getRandomTask() {
        return repository.getRandomTask();
    }

    @Override
    public List<MathTask> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public MathTask getTaskById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @PostConstruct
    private void init() {
        Random random = new Random();

        //Таблица умножения
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                repository.save(
                        MathTask.builder()
                                .task(i + " x " + j + " = ???")
                                .id(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE)
                                .correctAnswer(String.valueOf(i * j))
                                .answers(Stream.of(
                                                String.valueOf(i * j),
                                                String.valueOf(random.nextInt(90)),
                                                String.valueOf(random.nextInt(90)),
                                                String.valueOf(random.nextInt(90)))
                                        .collect(Collectors.toSet()))
                                .build()
                );
            }
        }
    }
}

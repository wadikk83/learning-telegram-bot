package by.wadikk.telegrambot.repository;

import by.wadikk.telegrambot.entity.Task;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TaskRepository {

    private List<Task> taskList = new ArrayList<>();

    public void addTask(Task task) {
        taskList.add(task);
    }

    public Task getRandomTask() {
        return taskList.get(new Random().nextInt(taskList.size() - 1));
    }

    public List<Task> getAllTasks() {
        return taskList;
    }

    public Task getTaskById(Long id) {
        //todo optional
        return taskList.stream()
                .filter(it -> it.getId().longValue() == id.longValue())
                .findFirst()
                .get();
    }

    @PostConstruct
    void init() {
        Random random = new Random();

        //Таблица умножения
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                taskList.add(
                        Task.builder()
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

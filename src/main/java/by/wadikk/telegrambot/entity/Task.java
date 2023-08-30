package by.wadikk.telegrambot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@MappedSuperclass
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "task")
    private String task;

    @NotNull
    @Column(name = "correct_answer")
    private String correctAnswer;

    @ElementCollection
    private Set<String> answers;

}

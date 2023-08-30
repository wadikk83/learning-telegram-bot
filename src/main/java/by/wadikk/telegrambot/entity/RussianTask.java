package by.wadikk.telegrambot.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "russian_task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RussianTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "task")
    private String task;

    @NotNull
    @Column(name = "correct_answer")
    private String correctAnswer;

    @NotNull
    @ElementCollection
    @CollectionTable(name = "math_task_answers", joinColumns = @JoinColumn(name = "math_task_id"))
    @Column(name = "answers")
    private Set<String> answers;
}

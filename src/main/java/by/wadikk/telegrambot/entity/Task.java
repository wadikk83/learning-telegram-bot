package by.wadikk.telegrambot.entity;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    private Long id;

    private String task;

    private String correctAnswer;

    private Set<String> answers;

}

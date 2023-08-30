package by.wadikk.telegrambot.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @NotNull
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "total_correct_answers")
    private Integer totalCorrectAnswers;

    @Column(name = "total_answers")
    private Integer totalAnswers;
}

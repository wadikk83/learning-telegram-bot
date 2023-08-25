package by.wadikk.demotelegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    /**
     * Пример
     */
    String example;

    /**
     * Варианты решения
     */
    Set<String> mistakes;

}

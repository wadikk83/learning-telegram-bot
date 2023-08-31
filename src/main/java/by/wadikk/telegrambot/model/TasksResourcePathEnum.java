package by.wadikk.telegrambot.model;

import lombok.Getter;

public enum TasksResourcePathEnum {
    FILE1("russian", "./tasks/Russian.xlsx"),
    FILE2("math", "./tasks/Math.xlsx");

    @Getter
    private final String attribute;

    @Getter
    private final String fileName;


    TasksResourcePathEnum(String attribute, String fileName) {
        this.attribute = attribute;
        this.fileName = fileName;
    }
}

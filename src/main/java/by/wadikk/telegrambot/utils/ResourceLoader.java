package by.wadikk.telegrambot.utils;

import by.wadikk.telegrambot.model.TasksResourcePathEnum;
import lombok.Getter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ResourceLoader {

    //по какому предмету задание/Workbook
    @Getter
    private final Map<String, XSSFWorkbook> defaultTasks;

    public ResourceLoader() throws IOException {
        this.defaultTasks = loadAllDefaultTaskWorkbooks();
    }

    private Map<String, XSSFWorkbook> loadAllDefaultTaskWorkbooks() throws IOException {
        Map<String, XSSFWorkbook> defaultTasks = new HashMap<>();
        for (TasksResourcePathEnum path : TasksResourcePathEnum.values()) {
            defaultTasks.put(path.getAttribute(), loadWorkbook(path.getFileName()));
        }
        return defaultTasks;
    }

    private XSSFWorkbook loadWorkbook(String filePath) throws IOException {
        return new XSSFWorkbook(
                Objects.requireNonNull(
                        getClass()
                                .getClassLoader()
                                .getResourceAsStream(filePath)
                )
        );
    }
}

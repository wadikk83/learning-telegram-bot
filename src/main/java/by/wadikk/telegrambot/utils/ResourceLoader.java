package by.wadikk.telegrambot.utils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResourceLoader {

    private final Map<String, XSSFWorkbook> defaultTasks;

    public ResourceLoader() {
        this.defaultTasks = loadAllDefaultTaskWorkbooks();
    }

    private Map<String, XSSFWorkbook> loadAllDefaultTaskWorkbooks() {
        Map<String, XSSFWorkbook> defaultTasks = new HashMap<>();
        for (DictionaryResourcePathEnum path : DictionaryResourcePathEnum.values()) {
            defaultDictionaries.put(path.name(), loadWorkbook(path.getFilePath()));
        }
        return defaultDictionaries;
    }
}

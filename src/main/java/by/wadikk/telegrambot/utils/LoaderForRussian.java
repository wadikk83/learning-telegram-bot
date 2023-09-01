package by.wadikk.telegrambot.utils;

import by.wadikk.telegrambot.entity.RussianTask;
import by.wadikk.telegrambot.exceptions.TasksTooBigException;
import by.wadikk.telegrambot.service.RussianTaskService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoaderForRussian {

    private final RussianTaskService service;

    public LoaderForRussian(RussianTaskService russianTaskService) {
        this.service = russianTaskService;
    }

    public void addDefaultTasks(XSSFWorkbook workbook) {
        loadTaskFromWorkbook(workbook).stream()
                .forEach(task -> service.addTask(task));
    }

    private List<RussianTask> loadTaskFromWorkbook(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        List<RussianTask> result = new ArrayList<>();
        while (rowIterator.hasNext()) {
            result.add(createTaskFromRow(rowIterator.next()));
        }
        result.remove(0);

        if (result.size() > 1000) {
            throw new TasksTooBigException();
        }
        return result;
    }

    private RussianTask createTaskFromRow(Row row) {
        Iterator<Cell> cellIterator = row.iterator();

        List<String> line = new ArrayList<>();
        while (cellIterator.hasNext()) {
            line.add(cellIterator.next().getStringCellValue());
        }
        return RussianTask.builder()
                .task(line.get(0))
                .correctAnswer(line.get(1))
                .answers(
                        line.stream()
                                .skip(1)
                                .collect(Collectors.toSet()))
                .build();
    }
}

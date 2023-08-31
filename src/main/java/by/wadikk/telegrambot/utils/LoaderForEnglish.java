package by.wadikk.telegrambot.utils;

import by.wadikk.telegrambot.entity.EnglishTask;
import by.wadikk.telegrambot.entity.MathTask;
import by.wadikk.telegrambot.exceptions.TasksTooBigException;
import by.wadikk.telegrambot.service.EnglishTaskService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class LoaderForEnglish {

    private final EnglishTaskService service;

    public LoaderForEnglish(EnglishTaskService service) {
        this.service = service;
    }


    public void addDefaultTasks(XSSFWorkbook workbook) {
        loadTaskFromWorkbook(workbook).stream()
                .forEach(task -> service.addTask(task));
    }

    private List<EnglishTask> loadTaskFromWorkbook(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        List<EnglishTask> result = new ArrayList<>();
        while (rowIterator.hasNext()) {
            result.add(createTaskFromRow(rowIterator.next()));
        }
        result.remove(0);

        if (result.size() > 1000) {
            throw new TasksTooBigException();
        }
        return result;
    }

    private EnglishTask createTaskFromRow(Row row) {
        Iterator<Cell> cellIterator = row.iterator();

        List<String> line = new ArrayList<>();
        while (cellIterator.hasNext()) {
            line.add(cellIterator.next().getStringCellValue());
        }
        return EnglishTask.builder()
                .task(line.get(0))
                .correctAnswer(line.get(1))
                .answers(
                        line.stream()
                                .skip(1)
                                .collect(Collectors.toSet()))
                .build();
    }
}

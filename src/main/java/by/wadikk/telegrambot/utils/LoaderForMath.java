package by.wadikk.telegrambot.utils;

import by.wadikk.telegrambot.entity.MathTask;
import by.wadikk.telegrambot.exceptions.TasksTooBigException;
import by.wadikk.telegrambot.service.MathTaskService;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoaderForMath {

    private final MathTaskService service;

    public LoaderForMath(MathTaskService service) {
        this.service = service;
    }

    public void addDefaultTasks(XSSFWorkbook workbook) {
        loadTaskFromWorkbook(workbook)
                .forEach(task -> service.addTask(task));
    }

    private List<MathTask> loadTaskFromWorkbook(XSSFWorkbook workbook) {
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();

        List<MathTask> result = new ArrayList<>();
        while (rowIterator.hasNext()) {
            result.add(createTaskFromRow(rowIterator.next()));
        }
        result.remove(0);

        if (result.size() > 1000) {
            throw new TasksTooBigException();
        }
        return result;
    }

    private MathTask createTaskFromRow(Row row) {
        DataFormatter formatter = new DataFormatter();
        List<String> line = new ArrayList<>();

        for (int i = 0; i < row.getLastCellNum(); i++) {
            line.add(formatter.formatCellValue(row.getCell(i)));
        }
        return MathTask.builder()
                .task(line.get(0))
                .correctAnswer(line.get(1))
                .answers(
                        line.stream()
                                .skip(1)
                                .collect(Collectors.toSet()))
                .build();
    }
}

package by.wadikk.telegrambot.utils;

import by.wadikk.telegrambot.entity.RussianTask;
import by.wadikk.telegrambot.service.RussianTaskService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadCSVFile {

    private final RussianTaskService service;

    public ReadCSVFile(RussianTaskService service) {
        this.service = service;
    }

    public void readFile(String filename) throws IOException {

        BufferedReader reader = new BufferedReader(
                new FileReader(filename));

        // считываем построчно
        String line = null;
        Scanner scanner = null;
        int index = 0;
        RussianTask task = new RussianTask();

        while ((line = reader.readLine()) != null) {
            scanner = new Scanner(line);
            scanner.useDelimiter(",");
            while (scanner.hasNext()) {
                String data = scanner.next();
                if (index == 0)
                    System.out.println(data);
                else if (index == 1)
                    System.out.println(data);
                else if (index == 2)
                    System.out.println(data);
                else if (index == 3)
                    System.out.println(data);
                else
                    System.out.println("Некорректные данные::" + data);
                index++;
            }
            index = 0;
            service.addTask(task);
        }

        //закрываем наш ридер
        reader.close();
    }
}

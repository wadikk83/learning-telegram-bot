package by.wadikk.telegrambot.initialization;

import by.wadikk.telegrambot.utils.LoadFromExcelService;
import by.wadikk.telegrambot.utils.ResourceLoader;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Initialize implements InitializingBean {

    private final ResourceLoader resourceLoader;

    private final LoadFromExcelService service;

    public Initialize(ResourceLoader resourceLoader, LoadFromExcelService service) {
        this.resourceLoader = resourceLoader;
        this.service = service;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, XSSFWorkbook> defaultDictionaryMap = resourceLoader.getDefaultTasks();
        for (Map.Entry<String, XSSFWorkbook> pair : defaultDictionaryMap.entrySet()) {
            service.addDefaultTasks(pair.getKey(), pair.getValue());
        }
    }
}

package by.wadikk.telegrambot.utils;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class LoadFromExcelService {
    private final LoaderForRussian loaderForRussian;

    private final LoaderForMath loaderForMath;

    public LoadFromExcelService(LoaderForRussian loaderFromRussian,
                                LoaderForMath loaderForMath) {
        this.loaderForRussian = loaderFromRussian;
        this.loaderForMath = loaderForMath;
    }

    public void addDefaultTasks(String attr, XSSFWorkbook workbook) {
        if (attr.equals("russian")) {
            loaderForRussian.addDefaultTasks(workbook);
        } else if (attr.equals("math")) {
            loaderForMath.addDefaultTasks(workbook);
        }
    }
}



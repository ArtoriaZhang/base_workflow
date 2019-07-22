package com.els.base.workflow.common.utils;

import com.els.base.core.utils.Constant;
import com.els.base.core.utils.project.ProjectUtils;
import com.els.base.file.entity.FileData;
import com.els.base.file.service.FileManagerFactory;
import com.els.base.utils.excel.ExcelUtils;
import com.els.base.utils.excel.TitleAndModelKey;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class ExcelFileData {
    public static FileData createExcelFileOutputStream(List<TitleAndModelKey> titleAndModelKeys,
            List<? extends Object> list, String sheetTitle, String headTitle, int i)
            throws IOException, WriteException, ParseException {
        FileData fileData = new FileData();
        String dateStr = DateFormatUtils.format(new Date(), "yyyyMMdd");
        fileData.setProjectId(ProjectUtils.getProjectId());
        fileData.setFileName(MessageFormat.format(headTitle + "-{0}", new Object[] { dateStr }) + ".xls");
        fileData.setFileSuffix("xls");
        fileData.setIsEncrypt(String.valueOf(Constant.NO_INT));
        fileData.setExpiryDay(DateUtils.addDays(new Date(), 2));
        fileData = FileManagerFactory.getFileManager().write(new ByteArrayInputStream("".getBytes("UTF-8")), fileData);
        FileOutputStream outputStream = new FileOutputStream(fileData.toFile());
        WritableWorkbook writableWorkbook = ExcelUtils.exportDataToExcel(outputStream, titleAndModelKeys, list,
                sheetTitle, null, i);
        writableWorkbook.write();
        outputStream.flush();
        writableWorkbook.close();
        outputStream.close();
        return fileData;
    }
}

package com.alsace.exchange.web.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.util.PoiCellUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @description: 文件导出导入工具类
 * @author: wayne
 * @create: 2021/01/12 16:41
 */
@Slf4j
public class ExportUtil {

    /***
     * 导出数据
     *          数据，数据类，  标题，   sheet名字 ， 文件名称带后缀，是否创建header，HttpServletResponse
     * @Param [list, pojoClass, title, sheetName, fileName, isCreateHeader, response]
     * @Return void
     * @Author wayne
     * @Date 2020/2/7 18:12
     **/
    public static void exportExcel(List<?> list, Class<?> pojoClass, String title, String sheetName, String fileName, boolean isCreateHeader, HttpServletResponse response) throws Exception {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response) throws Exception {
        ExportParams exportParams = new ExportParams();
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    public static void exportExcel(List<?> list, Class<?> pojoClass, String title, String sheetName, String fileName, HttpServletResponse response) throws Exception {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws Exception {
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) throws Exception {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    public static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws Exception {
        OutputStream outputStream = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            outputStream = response.getOutputStream();

            workbook.write(outputStream);
        } catch (IOException e) {
            throw new Exception("");
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws Exception {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }

    /***
     * 根据模板导入数据
     * @Param [filePath, titleRows, headerRows, pojoClass]
     * @Return java.util.List<T>
     * @Author wayne
     * @Date 2021/01/12 16:44
     **/
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws Exception {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new Exception("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return list;
    }

    /***
     * 直接导入数据
     * @Param [file, titleRows, headerRows, pojoClass]
     * @Return java.util.List<T>
     * @Author wayne
     * @Date 2021/01/12 16:44
     **/
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) throws Exception {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new Exception("excel文件不能为空");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return list;
    }

    /**
     * 设置下拉框数据
     */
    public static void setDropDown(Class<?> cls, Workbook workbook) {
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            ExcelDropDown evd = f.getAnnotation(ExcelDropDown.class);
            if (evd == null) {
                continue;
            }
            Set<String> dropSet = Sets.newHashSet();
            DropDownEnum[] dowmEnums = DropDownEnum.values();
            for (DropDownEnum dowmEnum : dowmEnums) {
                if (evd.dropType() == dowmEnum.getValue()) {
                    dropSet = dowmEnum.getMap().keySet();
                }
            }
            CellRangeAddressList addressList = new CellRangeAddressList(evd.firstRow(), evd.lastRow(), evd.firstCol(), evd.lastCol());
            DataValidationHelper helper = workbook.getSheetAt(0).getDataValidationHelper();
            DataValidationConstraint constraint = helper.createExplicitListConstraint(dropSet.toArray(new String[]{}));
            DataValidation dataValidation = helper.createValidation(constraint, addressList);
            //处理Excel兼容性问题
            if (dataValidation instanceof XSSFDataValidation) {
                dataValidation.setSuppressDropDownArrow(true);
                dataValidation.setShowErrorBox(true);
            } else {
                dataValidation.setSuppressDropDownArrow(false);
            }
            workbook.getSheetAt(0).addValidationData(dataValidation);
        }
    }

    /**
     * 校验模板非法
     */
    public static <T> Boolean checkIlegalTemplate(Class<T> pojoClass, MultipartFile file) {
        InputStream excelInputStream = null;
        try {
            //获取表头信息
            excelInputStream = file.getInputStream();
            Workbook book = WorkbookFactory.create(file.getInputStream());
            Workbook workBook = null;
            if (book instanceof XSSFWorkbook) {
                workBook = (XSSFWorkbook) book;
            } else if (book instanceof HSSFWorkbook) {
                workBook = (HSSFWorkbook) book;
            }
            Sheet sheet = workBook.getSheetAt(0);
            Row row = sheet.getRow(1);
            Iterator<Cell> cellTitle = row.cellIterator();
            List<String> titleList = new ArrayList<>();
            while (cellTitle.hasNext()) {
                Cell cell = cellTitle.next();
                String value = getKeyValue(cell);
                value = value.replace("\n", "");
                titleList.add(value);
            }
            titleList.removeAll(Collections.singleton(""));

            List<String> fieldList = new ArrayList<>();
            Field[] fields = pojoClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Excel excel = field.getAnnotation(Excel.class);
                if (null != excel) {
                    fieldList.add(excel.name());
                }
            }
            if (!titleList.equals(fieldList)) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            log.info("checkIlegalTemplate error {}", Throwables.getStackTraceAsString(e));
        } finally {
            if (excelInputStream != null) {
                try {
                    excelInputStream.close();
                } catch (IOException e) {
                    log.error("IOException:{}:{}, Error:{}", e.getClass().getName(), e.getLocalizedMessage(),
                            e.getStackTrace()[0].toString(), e);
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 获取key的值,针对不同类型获取不同的值
     *
     * @author JueYue 2013-11-21
     */
    private static String getKeyValue(Cell cell) {
        Object obj = PoiCellUtil.getCellValue(cell);
        return obj == null ? null : obj.toString().trim();
    }

}

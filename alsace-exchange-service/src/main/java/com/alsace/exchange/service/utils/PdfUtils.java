package com.alsace.exchange.service.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailImport;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 *  PDF工具类
 * @Author: wayne
 * @Date: 2021/1/24
 */
@Slf4j
public class PdfUtils {


    public static void addPdfTitle(PdfPTable table, Font fontBold, String title) {
        //中间部分填充
        PdfPCell cell = PdfUtils.mergeCol(title, fontBold, 5, false);
        cell.disableBorderSide(15);
        table.addCell(cell);
    }

    public static void addPdfTable(PdfPTable table, Font fontBold, Font font2, List<PersonTaskDetailImport> details) {
        table.addCell(PdfUtils.getPDFCell("姓名", fontBold, true));
        table.addCell(PdfUtils.getPDFCell("身份证", fontBold, true));
        table.addCell(PdfUtils.getPDFCell("采样管码", fontBold, true));
        table.addCell(PdfUtils.getPDFCell("检测时间", fontBold, true));
        table.addCell(PdfUtils.getPDFCell("检测结果", fontBold, true));
        details.forEach(detail -> {
            table.addCell(PdfUtils.getPDFCell(detail.getPersonName(), font2, false));
            table.addCell(PdfUtils.getPDFCell(detail.getIdCardNo(), font2, false));
            table.addCell(PdfUtils.getPDFCell(detail.getPersonName(), font2, false));
            table.addCell(PdfUtils.getPDFCell(detail.getIdCardNo(), font2, false));
            table.addCell(PdfUtils.getPDFCell(detail.getPersonName(), font2, false));
        });
    }

    public static void addEnvPdfTable(PdfPTable table, Font fontBold, Font font2, List<EnvironmentTaskDetailImport> details) {
        table.addCell(PdfUtils.getPDFCell("姓名", fontBold, true));
        table.addCell(PdfUtils.getPDFCell("身份证", fontBold, true));
        table.addCell(PdfUtils.getPDFCell("采样管码", fontBold, true));
        table.addCell(PdfUtils.getPDFCell("检测时间", fontBold, true));
        table.addCell(PdfUtils.getPDFCell("检测结果", fontBold, true));
//        details.forEach(detail -> {
//            table.addCell(PdfUtils.getPDFCell(detail.get, font2, false));
//            table.addCell(PdfUtils.getPDFCell(detail.getIdCardNo(), font2, false));
//            table.addCell(PdfUtils.getPDFCell(detail.getPersonName(), font2, false));
//            table.addCell(PdfUtils.getPDFCell(detail.getIdCardNo(), font2, false));
//            table.addCell(PdfUtils.getPDFCell(detail.getPersonName(), font2, false));
//        });
    }


    private void addPdfHead(PdfPTable table, Font fontBold, Font font2) {
        PdfPCell cell = PdfUtils.getPDFCell("打印日期", fontBold, true);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(PdfUtils.mergeCol(DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATE_PATTERN), font2, 2, false));

    }


    //获取指定内容与字体的单元格
    public static PdfPCell getPDFCell(String string, Font font, Boolean isBgColor) {
        //创建单元格对象，将内容与字体放入段落中作为单元格内容
        PdfPCell cell = new PdfPCell(new Paragraph(string, font));
        //设置背景颜色为浅灰
        if (isBgColor) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        //水平对齐
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //垂直对齐
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        //设置最小单元格高度
        cell.setMinimumHeight(25);
        return cell;
    }

    //合并列的静态函数
    public static PdfPCell mergeCol(String str, Font font, int i, Boolean isBgColor) {
        PdfPCell cell = new PdfPCell(new Paragraph(str, font));
        //设置背景颜色为浅灰
        if (isBgColor) {
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        }
        cell.setMinimumHeight(25);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        //将该单元格所在行包括该单元格在内的i列单元格合并为一个单元格
        cell.setColspan(i);
        return cell;
    }
}

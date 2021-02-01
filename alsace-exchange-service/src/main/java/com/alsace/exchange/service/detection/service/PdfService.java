package com.alsace.exchange.service.detection.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskTag;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  PDF工具类
 * @Author: wayne
 * @Date: 2021/1/24
 */
@Slf4j
@Service
public class PdfService{

    @Resource
    private PersonTaskDetailService personTaskDetailService;

    private ByteArrayOutputStream convertReceivePdf(String taskCode) throws IOException, DocumentException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //设值字体样式
        BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font fontBold = new Font(bf, 11, Font.BOLD);
        Font font2 = new Font(bf, 11, Font.NORMAL);
        // 页面大小
        Rectangle tRectangle = new Rectangle(PageSize.A4);
        // 定义文档
        Document doc = new Document(tRectangle, 20, 20, 20, 20);
        // doc = new Document(tRectangle.rotate());// 横向打印
        // 书写器
        PdfWriter writer = PdfWriter.getInstance(doc, out);
        //版本(默认1.4)
        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_2);
        PdfPTable table = new PdfPTable(5);
        table.setTotalWidth(new float[]{100, 100, 100, 100, 100});
        //添加表格内容
        //添加PDF标题内容
//        this.addPdfTitle(table, fontBold, "");
        //添加PDF头内容
        this.addPdfHead(table, fontBold, font2);
        List<PersonTaskDetail> details = personTaskDetailService.findAll(new PersonTaskDetail());
        //添加导出信息
        addPdfTable(table, fontBold, font2, details);

        PdfPCell cell = PdfService.mergeCol("归档使用", fontBold, 5, false);
        cell.disableBorderSide(15);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cell);

        //打开文档
        doc.open();
        //添加img
        doc.add(table);
        //记得关闭document
        doc.close();
        return out;
    }

    private static void addPdfTable(PdfPTable table, Font fontBold, Font font2, List<PersonTaskDetail> details) {
        table.addCell(PdfService.getPDFCell("姓名", fontBold, true));
        table.addCell(PdfService.getPDFCell("身份证", fontBold, true));
        table.addCell(PdfService.getPDFCell("采样管码", fontBold, true));
        table.addCell(PdfService.getPDFCell("检测时间", fontBold, true));
        table.addCell(PdfService.getPDFCell("检测结果", fontBold, true));
        details.forEach(detail -> {
            table.addCell(PdfService.getPDFCell(detail.getPersonName(), font2, false));
            table.addCell(PdfService.getPDFCell(detail.getIdCardNo(), font2, false));
//            table.addCell(PdfUtils.getPDFCell(String.valueOf(detail.getCnyNoTaxAmount()), font2, false));
//            //税额 = 金额 * 税率
//            table.addCell(PdfUtils.getPDFCell(String.valueOf(detail.getCnyTaxAmount()), font2, false));
        });
    }


    private void addPdfHead(PdfPTable table, Font fontBold, Font font2) {
        PdfPCell cell = PdfService.getPDFCell("打印日期", fontBold, true);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
        table.addCell(PdfService.mergeCol(DateUtil.format(LocalDateTime.now(), DatePattern.NORM_DATE_PATTERN), font2, 2, false));

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

package com.alsace.exchange.web.detection.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.service.detection.domain.*;
import com.alsace.exchange.service.detection.emums.TaskDetailStatus;
import com.alsace.exchange.service.detection.service.EnvironmentTaskDetailService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import com.alsace.exchange.web.utils.ExportUtil;
import com.google.common.collect.Lists;
import com.itextpdf.text.DocumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "环境监测任务明细",value = "environmentTaskDetail")
@RestController
@RequestMapping("/env-task/detail")
public class EnvironmentTaskDetailController extends BaseController {

  @Resource
  private EnvironmentTaskDetailService environmentTaskDetailService;
  @Resource
  private OrderNoGenerator orderNoGenerator;

  @ApiOperation("环境监测任务明细分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<EnvironmentTaskDetail>> page(@RequestBody PageParam<EnvironmentTaskDetail> param) {
    PageResult<EnvironmentTaskDetail> page = environmentTaskDetailService.findPage(param);
    return success(page);
  }

  @ApiOperation("环境监测任务明细更新")
  @PostMapping("/update")
  public AlsaceResult<EnvironmentTaskDetail> update(@RequestBody EnvironmentTaskDetail param) {
    EnvironmentTaskDetail domain = environmentTaskDetailService.update(param);
    return success(domain);
  }

  @ApiOperation("环境监测任务明细删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    environmentTaskDetailService.delete(idList);
    return success("删除成功",null);
  }

  @ApiOperation("环境检测任务明细保存")
  @PostMapping("/save")
  public AlsaceResult<EnvironmentTaskDetail> save(@RequestBody EnvironmentTaskDetail param) {
    return success(environmentTaskDetailService.saveDetail(param));
  }

  @ApiOperation("被检测环境信息导入模板下载")
  @RequestMapping("/downTemplate")
  public void downTemplate(HttpServletResponse response)  {
    List<PersonTaskDetailImport> list = Lists.newArrayList();
    Workbook workBook = ExcelExportUtil.exportExcel(new ExportParams("被检测环境信息导入模板", "被检测环境信息"), EnvironmentTaskDetailImport.class, list);
    try(OutputStream out = response.getOutputStream()) {
      response.setContentType("application/ms-excel;charset=UTF-8");
      response.setHeader("Content-Disposition", "attachment;filename="
              .concat(String.valueOf(URLEncoder.encode("被检测环境信息导入模板.xls", "UTF-8"))));
      workBook.write(out);
    } catch(Exception e) {
      error("101", Constants.SYSTEM_ERROR);
    }

  }

  @ApiOperation("被检测环境信息导入")
  @PostMapping("/importDetails")
  public AlsaceResult<List<EnvironmentTaskDetail>> importDetails(@ApiParam(name = "文件", value = "file", required = true)
                                                            @RequestParam("file") MultipartFile file,
                                                            @ApiParam(name = "任务编码", value = "taskCode", required = true) @RequestParam("taskCode") String taskCode) {
    if (!file.getOriginalFilename().toLowerCase().endsWith("xls") && !file.getOriginalFilename().toLowerCase().endsWith("xlsx")) {
      return error("102", Constants.FORMAT_ERROR);
    }
    if (ExportUtil.checkIlegalTemplate(EnvironmentTaskDetailImport.class, file)) {
      return error("102",Constants.TEMPLATE_ERROR);
    }
    try {
      return success(environmentTaskDetailService.importDetails(Lists.newArrayList(file.getBytes()),taskCode));
    } catch (IOException e) {
      return error("102",Constants.IMPORT_ERROR);
    }
  }


  @ApiOperation("环境检测结果分页查询")
  @PostMapping("/resultPage")
  public  AlsaceResult<PageResult<EnvironmentTaskDetail>> resultPage(@RequestBody PageParam<EnvironmentTaskDetail> param){
    PageResult<EnvironmentTaskDetail> page = environmentTaskDetailService.findResultPage(param);
    return success(page);
  }

  @ApiOperation("环境检测结果导出")
  @RequestMapping("/resultExport")
  public void resultExport(HttpServletResponse response,@RequestBody EnvironmentTaskDetail param){
    List<EnvironmentTaskDetailImport> list = environmentTaskDetailService.findResults(param);
    Workbook workBook = ExcelExportUtil.exportExcel(new ExportParams("环境检测报告", "环境检测报告"), EnvironmentTaskDetailImport.class, list);
    LocalDateTime time=LocalDateTime.now();
    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String excelName="环境检测报告"+time.format(pattern)+".xls";
    try(OutputStream out = response.getOutputStream()) {
      response.setContentType("application/ms-excel;charset=UTF-8");
      response.setHeader("Content-Disposition", "attachment;filename="
              .concat(String.valueOf(URLEncoder.encode(excelName, "UTF-8"))));
      workBook.write(out);
    } catch(Exception e) {
      error("101", Constants.SYSTEM_ERROR);
    }
  }

  @ApiOperation("环境检测结果PDF导出")
  @RequestMapping("/resultPdf")
  public void resultPdf(HttpServletResponse response,@RequestBody EnvironmentTaskDetail param){
    try{
      ByteArrayOutputStream outputStream=environmentTaskDetailService.convertReceivePdf(param);
      response.setContentType(response.getContentType());
      LocalDateTime time=LocalDateTime.now();
      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      String pdfName="环境检测报告"+time.format(pattern)+".pdf";
      response.setHeader("Content-Disposition","attachment; filename=" + URLEncoder.encode( pdfName, "UTF-8"));
      byte[] bytes = outputStream.toByteArray();
      BufferedOutputStream bos = null;
      bos = new BufferedOutputStream(response.getOutputStream());
      bos.write(bytes);
      bos.close();

    } catch (DocumentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

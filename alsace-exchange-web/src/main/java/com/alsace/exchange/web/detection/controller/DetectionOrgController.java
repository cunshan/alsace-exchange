package com.alsace.exchange.web.detection.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.base.TreeVo;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.service.detection.domain.DetectionOrg;
import com.alsace.exchange.service.detection.domain.DetectionOrgImport;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.alsace.exchange.service.detection.service.DetectionOrgService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import com.alsace.exchange.web.detection.biz.DetectionOrgHandler;
import com.alsace.exchange.web.utils.ChineseCharacterUtil;
import com.alsace.exchange.web.utils.ExportUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = "检测机构",value = "detectionOrg")
@RestController
@RequestMapping("/detection/org")
public class DetectionOrgController extends BaseController {

  @Resource
  private DetectionOrgService detectionOrgService;
  @Resource
  private DetectionOrgHandler detectionOrgHandler;
  @Resource
  private OrderNoGenerator orderNoGenerator;


  @ApiOperation("检测机构保存")
  @PostMapping("/save")
  public AlsaceResult<DetectionOrg> save(@RequestBody DetectionOrg param) {
    param.setOrgCode(orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.ORG_CODE));
    return success(detectionOrgService.save(param));
  }

  @ApiOperation("检测机构分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<DetectionOrg>> page(@RequestBody PageParam<DetectionOrg> param) {
    PageResult<DetectionOrg> page = detectionOrgService.findPage(param);
    return success(page);
  }

  @ApiOperation("检测机构更新")
  @PostMapping("/update")
  public AlsaceResult<DetectionOrg> update(@RequestBody DetectionOrg param) {
    DetectionOrg domain = detectionOrgService.update(param);
    return success(domain);
  }

  @ApiOperation("检测机构删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    detectionOrgService.delete(idList);
    return success("删除成功",null);
  }

  @ApiOperation("检测机构树结构查询")
  @PostMapping("/tree/{parentCode}")
  public AlsaceResult<List<TreeVo<DetectionOrg>>> tree(@PathVariable String parentCode) {
    return success(detectionOrgHandler.tree(parentCode));
  }

  @ApiOperation("所有检测机构树结构查询")
  @PostMapping("/tree")
  public AlsaceResult<List<TreeVo<DetectionOrg>>> treeAll() {
    return success(detectionOrgHandler.tree(null));
  }

  @ApiOperation("检测机构导入模板下载")
  @RequestMapping("/downTemplate")
  public void downTemplate(HttpServletResponse response)  {
    List<DetectionOrg> list = Lists.newArrayList();
    Workbook workBook = ExcelExportUtil.exportExcel(new ExportParams("检测机构导入模板", "检测机构"), DetectionOrgImport.class, list);
    try(OutputStream out = response.getOutputStream()) {
      response.setContentType("application/ms-excel;charset=UTF-8");
      response.setHeader("Content-Disposition", "attachment;filename="
              .concat(String.valueOf(URLEncoder.encode("检测机构导入模板.xls", "UTF-8"))));
      workBook.write(out);
    } catch(Exception e) {
      error("101", Constants.SYSTEM_ERROR);
    }

  }

  @ApiOperation("检测机构导入")
  @PostMapping("/importOrg")
  public AlsaceResult<List<DetectionOrg>> importDetails(@ApiParam(name = "文件", value = "file", required = true)
                                                            @RequestParam("file") MultipartFile file,
                                                            @ApiParam(name = "父级编码", value = "orgCode", required = true) @RequestParam("orgCode") String orgCode,
                                                            @ApiParam(name = "父级名称", value = "orgName", required = true) @RequestParam("orgName") String orgName) {
    if (!file.getOriginalFilename().toLowerCase().endsWith("xls") && !file.getOriginalFilename().toLowerCase().endsWith("xlsx")) {
      return error("102", Constants.FORMAT_ERROR);
    }
    if (ExportUtil.checkIlegalTemplate(DetectionOrgImport.class, file)) {
      return error("102",Constants.TEMPLATE_ERROR);
    }
    try {
      return success(detectionOrgService.importOrg(Lists.newArrayList(file.getBytes()),orgCode,orgName));
    } catch (IOException e) {
      return error("102",Constants.IMPORT_ERROR);
    }
  }
}

package com.alsace.exchange.web.sys.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.common.validate.groups.Create;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.domain.UserImport;
import com.alsace.exchange.service.sys.service.UserService;
import com.alsace.exchange.web.sys.vo.UserRoleVo;
import com.alsace.exchange.web.utils.ExportUtil;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Api(tags = "用户",value = "user")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

  @Resource
  private UserService userService;

  @ApiOperation("用户保存")
  @PostMapping("/save")
  public AlsaceResult<User> save(@RequestBody @Validated(Create.class) User param) {
    param.setPassword(DigestUtils.md5Hex(param.getPassword().trim() + param.getLoginAccount()));
    User domain = userService.save(param);
    return success(domain);
  }

  @ApiOperation("分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<User>> page(@RequestBody PageParam<User> param) {
    PageResult<User> page = userService.findPage(param);
    return success(page);
  }

  @ApiOperation("用户更新")
  @PostMapping("/update")
  public AlsaceResult<User> update(@RequestBody User param) {
    param.setPassword(null);
    param.setLoginAccount(null);
    User domain = userService.update(param);
    return success(domain);
  }

  @ApiOperation("用户删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    userService.delete(idList);
    return success("删除成功",null);
  }

  @ApiOperation("修改用户角色")
  @PostMapping("/add-roles")
  public AlsaceResult<String> addUserRoles(@RequestBody UserRoleVo param){
    userService.addUserRoles(param.getLoginAccount(),param.getRoleList());
    return success("更新成功！",null);
  }

  @ApiOperation("用户导入模板下载")
  @PostMapping("/downTemplate")
  public void downTemplate() throws IOException {
    List<UserImport> list = Lists.newArrayList();
    Workbook workBook = ExcelExportUtil.exportExcel(new ExportParams(), UserImport.class, list);
    ExportUtil.setDropDown(UserImport.class, workBook);
    File outputFile = new File("用户导入模板.xls");
    if (!outputFile.exists()) {
      outputFile.createNewFile();
    }
    FileOutputStream fos = new FileOutputStream(outputFile);
    workBook.write(fos);
  }

  @ApiOperation("用户导入")
  @PostMapping("/import")
  public AlsaceResult<List<User>> importUser(@ApiParam(name = "文件", value = "file", required = true)
                                             @RequestParam("file") MultipartFile file) {
    if (!file.getOriginalFilename().toLowerCase().endsWith("xls") && !file.getOriginalFilename().toLowerCase().endsWith("xlsx")) {
      return error("101", Constants.FORMAT_ERROR);
    }
    if (ExportUtil.checkIlegalTemplate(UserImport.class, file)) {
      return error("101",Constants.TEMPLATE_ERROR);
    }
    try {
      return success(userService.importUser(Lists.newArrayList(file.getBytes())));
    } catch (IOException e) {
      return error("",Constants.IMPORT_ERROR);
    }
  }


}

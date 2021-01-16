package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.sys.domain.UserData;
import com.alsace.exchange.service.sys.service.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户数据权限",value = "userData")
@RestController
@RequestMapping("/user/data")
public class UserDataController extends BaseController {
  
  @Resource
  private UserDataService userDataService;


  @ApiOperation("用户数据权限值保存")
  @PostMapping("/save")
  public AlsaceResult<UserData> save(@RequestBody @Validated UserData param) {
    return success(userDataService.save(param));
  }

  @ApiOperation("用户数据权限值分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<UserData>> page(@RequestBody PageParam<UserData> param) {
    PageResult<UserData> page = userDataService.findPage(param);
    return success(page);
  }
  
  @ApiOperation("用户数据权限值更新")
  @PostMapping("/update")
  public AlsaceResult<UserData> update(@RequestBody UserData param) {
    UserData domain = userDataService.update(param);
    return success(domain);
  }

  @ApiOperation("用户数据权限值删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    userDataService.delete(idList);
    return success("删除成功",null);
  }


  @ApiOperation("按照用户数据权限编码查询用户数据权限值")
  @PostMapping("/find-by-type-code/{typeCode}")
  public AlsaceResult<List<UserData>> findByType(@PathVariable String typeCode){
    Assert.hasLength(typeCode,"用户数据权限编码为空！");
    UserData param = new UserData();
    param.setDataType(typeCode)
        .setDeleted(false);
    return success(userDataService.findAll(param));
  }

  @ApiOperation("用户数据权限值全量列表查询")
  @PostMapping("/list")
  public AlsaceResult<List<UserData>> page(@RequestBody UserData param) {
    param.setDeleted(false);
    return success(userDataService.findAll(param));
  }

}

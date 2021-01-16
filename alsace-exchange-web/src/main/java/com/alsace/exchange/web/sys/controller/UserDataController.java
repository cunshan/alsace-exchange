package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.service.sys.domain.UserData;
import com.alsace.exchange.service.sys.service.UserDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

  @ApiOperation("用户数据权限值全量列表查询")
  @PostMapping("/list")
  public AlsaceResult<List<UserData>> list(@RequestBody UserData param) {
    param.setDeleted(false);
    return success(userDataService.findAll(param));
  }



}

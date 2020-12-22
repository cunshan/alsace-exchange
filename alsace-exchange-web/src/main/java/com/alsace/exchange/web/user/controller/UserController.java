package com.alsace.exchange.web.user.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.user.domain.User;
import com.alsace.exchange.service.user.domain.UserRole;
import com.alsace.exchange.service.user.service.UserService;
import com.alsace.exchange.web.user.vo.UserRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "用户",value = "user")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

  @Resource
  private UserService userService;

  @ApiOperation("用户保存")
  @PostMapping("/save")
  public AlsaceResult<User> save(@RequestBody User param) {
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
  @PostMapping("/delete/{id}")
  public AlsaceResult<String> update(@PathVariable Long id) {
    userService.delete(id);
    return success("删除成功",null);
  }

  @ApiOperation("修改用户角色")
  @PostMapping("/add-role")
  public AlsaceResult<String> addUserRoles(@RequestBody UserRoleVo param){
    userService.addUserRoles(param.getLoginAccount(),param.getRoleList());
    return success("更新成功！",null);
  }



}

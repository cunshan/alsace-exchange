package com.alsace.exchange.user.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.user.domain.User;
import com.alsace.exchange.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

  @Resource
  private UserService userService;

  @PostMapping("/save")
  public AlsaceResult<User> save(@RequestBody User param) {
    param.setPassword(DigestUtils.md5Hex(param.getPassword().trim() + param.getLoginAccount()));
    User user = userService.save(param);
    return success(user);
  }


  @PostMapping("/page")
  public AlsaceResult<PageResult<User>> page(@RequestBody PageParam<User> param){
    PageResult<User> userPage = userService.findPage(param);
    return success(userPage);
  }


}

package com.alsace.exchange.web.user.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.service.user.domain.User;
import com.alsace.exchange.service.user.service.UserService;
import com.alsace.exchange.web.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.BearerToken;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController extends BaseController {

  @Resource
  private UserService userService;

  @PostMapping("/login")
  public AlsaceResult<String> login(@RequestBody User param) {
    User userParam = new User().setLoginAccount(param.getLoginAccount());
    User user = userService.findOne(userParam);
    Assert.state(user != null, "用户不存在！");
    Assert.state(!user.getLocked(), "用户已被锁定！");
    String password = DigestUtils.md5Hex(param.getPassword() + param.getLoginAccount());
    Assert.state(password.equals(user.getPassword()), "密码错误！");
    //密码匹配  登录成功
    BearerToken token = new BearerToken(JwtUtils.sign(user.getLoginAccount(), user.getPassword(), 60));
    SecurityUtils.getSubject().login(token);

    //TODO 记录登录日志
    return success("登录成功", token.getToken());
  }
}

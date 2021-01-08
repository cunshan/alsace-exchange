package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.TreeVo;
import com.alsace.exchange.service.sys.domain.Menu;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.service.MenuService;
import com.alsace.exchange.service.sys.service.UserService;
import com.alsace.exchange.web.config.shiro.jwt.JwtToken;
import com.alsace.exchange.web.sys.biz.MenuHandler;
import com.alsace.exchange.web.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户")
@RestController
public class LoginController extends BaseController {

  @Resource
  private UserService userService;
  @Resource
  private MenuHandler menuHandler;

  @ApiOperation(value = "用户登录", notes = "登录请求操作")
  @PostMapping("/login")
  public AlsaceResult<String> login(@RequestBody User param) {
    User userParam = new User().setLoginAccount(param.getLoginAccount());
    User user = userService.findOne(userParam);
    Assert.state(user != null, "用户不存在！");
    Assert.state(!user.getLocked(), "用户已被锁定！");
    String password = DigestUtils.md5Hex(param.getPassword() + param.getLoginAccount());
    Assert.state(password.equals(user.getPassword()), "密码错误！");
    //密码匹配  登录成功
    String tokenStr = JwtUtils.sign(user.getLoginAccount(), user.getUserName(), user.getPassword(), 60);
    JwtToken token = new JwtToken(tokenStr);
    SecurityUtils.getSubject().login(token);
    //TODO 记录登录日志
    return success("登录成功", token.getToken());
  }

  @ApiOperation(value = "登录用户显示的菜单", notes = "登录用户显示的菜单")
  @PostMapping("/current/menu")
  public AlsaceResult<List<TreeVo<Menu>>> currentUserMenu(){
    String loginAccount = getLoginAccount();
    return success(menuHandler.queryByLoginAccount(loginAccount));
  }
}

package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.TreeVo;
import com.alsace.exchange.service.sys.domain.Menu;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.web.sys.biz.LoginHandler;
import com.alsace.exchange.web.sys.biz.MenuHandler;
import com.alsace.exchange.web.sys.vo.AppLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户")
@RestController
public class LoginController extends BaseController {

  @Resource
  private MenuHandler menuHandler;
  @Resource
  private LoginHandler loginHandler;

  @ApiOperation(value = "用户登录", notes = "登录请求操作")
  @PostMapping("/login")
  public AlsaceResult<String> login(@RequestBody User param) {

    return success("登录成功", loginHandler.login(param));
  }

  @ApiOperation(value = "登录用户显示的菜单", notes = "登录用户显示的菜单")
  @PostMapping("/current/menu")
  public AlsaceResult<List<TreeVo<Menu>>> currentUserMenu() {
    String loginAccount = getLoginAccount();
    return success(menuHandler.queryByLoginAccount(loginAccount));
  }

  @ApiOperation(value = "APP登录", tags = "APP对应接口")
  @PostMapping("/app/login")
  public AlsaceResult<String> appLogin(@RequestBody AppLoginVo param) {
    return success(loginHandler.appLogin(param));
  }

  @ApiOperation(value = "APP登录", tags = "APP对应接口")
  @PostMapping("/app/send-check/{mobile}")
  public AlsaceResult<String> sendCheck(@PathVariable String mobile) {
    return success("发送成功！", loginHandler.sendCheck(mobile));
  }

}

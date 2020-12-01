package com.alsace.exchange.user.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.user.domain.User;
import com.alsace.exchange.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Assert;
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


    @PostMapping("/login")
    public AlsaceResult<String> login(@RequestBody User param) {
        User userParam = new User().setLoginAccount(param.getLoginAccount());
        User user = userService.findOne(userParam);
        Assert.state(user != null, "用户不存在！");
        Assert.state(!user.getLocked(), "用户已被锁定！");
        String password = DigestUtils.md5Hex(param.getPassword());
        Assert.state(!password.equals(user.getPassword()),"密码错误！");
        //密码匹配  登录成功
        //TODO 记录登录日志
        return success("登录成功！");
    }
}

package com.alsace.exchange.user.controller;

import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController extends BaseController {

    @Resource
    private UserService userService;


}

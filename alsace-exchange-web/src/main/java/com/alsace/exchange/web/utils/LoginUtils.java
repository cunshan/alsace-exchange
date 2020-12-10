package com.alsace.exchange.web.utils;

import com.alsace.exchange.web.config.shiro.jwt.JwtToken;
import org.apache.shiro.SecurityUtils;

/**
 * 登录工具类
 */
public class LoginUtils {


  /**
   * 登录名
   */
  public static String loginAccount() {
    return token().getLoginAccount();
  }

  /**
   * 登录人姓名
   */
  public static String userName() {
    return token().getUserName();
  }

  /**
   * 登录人所有信息
   */
  public static JwtToken token() {
    return (JwtToken) SecurityUtils.getSubject().getPrincipal();
  }
}

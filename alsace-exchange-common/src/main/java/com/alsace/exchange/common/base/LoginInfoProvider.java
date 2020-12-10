package com.alsace.exchange.common.base;

/**
 * 登录信息提供者
 */
public interface LoginInfoProvider {

  /**
   * 登录账号
   */
  String loginAccount();

  /**
   * 用户姓名
   */
  String userName();

}

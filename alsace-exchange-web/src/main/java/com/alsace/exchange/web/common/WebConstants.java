package com.alsace.exchange.web.common;

public class WebConstants {

  public static final String JWT_ERROR_MSG = "登录信息超时，请重新登录！";

  /**
   * token超时时间 分钟
   */
  public static final long JWT_TOKEN_TIMEOUT = 1440L;

  public static final String SHIRO_CACHE_PREFIX = "alsace:shiro:cache:";


}

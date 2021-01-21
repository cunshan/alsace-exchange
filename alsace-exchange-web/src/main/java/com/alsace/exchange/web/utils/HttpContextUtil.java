package com.alsace.exchange.web.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Http请求工具类
 *
 * @author zhangjingtao
 * @date 2020-1-14
 */
public class HttpContextUtil {

  public static HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
  }

  public static String getDomain() {
    HttpServletRequest request = getHttpServletRequest();
    StringBuffer url = request.getRequestURL();
    return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
  }

  public static String getOrigin() {
    HttpServletRequest request = getHttpServletRequest();
    return Optional.ofNullable(request.getHeader("Origin")).orElse("");
  }

  public static String getUserAgent() {
    HttpServletRequest request = getHttpServletRequest();
    return Optional.ofNullable(request.getHeader("user-agent")).orElse("");
  }

  public static String requestUrl() {
    return Optional.ofNullable(getHttpServletRequest().getRequestURI()).orElse("");
  }

  public static String requestMethod() {
    return Optional.ofNullable(getHttpServletRequest().getMethod()).orElse("");
  }

  public static String getIpAddr(HttpServletRequest request) {
    if (request == null) {
      return "unknown";
    }
    String ip = request.getHeader("x-forwarded-for");
    if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Forwarded-For");
    }
    if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Real-IP");
    }

    if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
  }

  public static String getRemoteAddr(){
    return getIpAddr(getHttpServletRequest());
  }

}

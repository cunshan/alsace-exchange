package com.alsace.exchange.web.config.shiro;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.utils.JsonUtils;
import com.alsace.exchange.web.common.WebConstants;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilter extends BearerHttpAuthenticationFilter {

//  /**
//   * 对跨域提供支持
//   */
//  @Override
//  protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
//    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//    httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//    httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
//    httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
//    // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
//    if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
//      httpServletResponse.setStatus(HttpStatus.OK.value());
//      return false;
//    }
//    return super.preHandle(request, response);
//  }


  @Override
  protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
    HttpServletResponse httpResponse = WebUtils.toHttp(response);
    AlsaceResult<String> result = new AlsaceResult<String>().setSuccess(false).setMsg(WebConstants.JWT_ERROR_MSG);
    //设置返回头信息
    httpResponse.setCharacterEncoding("UTF-8");
    httpResponse.setContentType("application/json;charset=UTF-8");
    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    try {
      httpResponse.getWriter().println(JsonUtils.toJson(result));
    } catch (IOException ex) {
      log.error(Throwables.getStackTraceAsString(ex));
    }
    return false;
  }


}

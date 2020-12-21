package com.alsace.exchange.web.config.shiro.jwt;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.utils.JsonUtils;
import com.alsace.exchange.web.common.WebConstants;
import com.alsace.exchange.web.config.redis.JsonRedisTemplate;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JwtFilter extends BearerHttpAuthenticationFilter {

  @Resource
  private JsonRedisTemplate jsonRedisTemplate;



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

  @Override
  protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
    //在登录成功之后，如果redis里有缓存，刷新缓存中的token时间
    JwtToken bearerToken = (JwtToken) token;
    jsonRedisTemplate.expire(WebConstants.SHIRO_CACHE_PREFIX + bearerToken.getPrincipal(), WebConstants.JWT_TOKEN_TIMEOUT, TimeUnit.MINUTES);
    return super.onLoginSuccess(token, subject, request, response);
  }

  @Override
  protected AuthenticationToken createBearerToken(String token, ServletRequest request) {
    return new JwtToken(token);
  }

}

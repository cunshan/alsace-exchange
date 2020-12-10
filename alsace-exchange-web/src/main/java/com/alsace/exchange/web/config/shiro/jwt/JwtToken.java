package com.alsace.exchange.web.config.shiro.jwt;

import com.alsace.exchange.web.utils.JwtUtils;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

@Data
public class JwtToken implements AuthenticationToken {

  private static final long serialVersionUID = -407575349075001944L;
  /**
   * token
   */
  private String token;
  /**
   * 登录账号
   */
  private String loginAccount;

  /**
   * 登录人姓名
   */
  private String userName;

  /**
   * host
   */
  private String host;

  public JwtToken(String token){
    this.token = token;
    this.userName = JwtUtils.getUserName(token);
    this.loginAccount = JwtUtils.getLoginAccount(token);
  }

  /**
   * redis缓存的key
   */
  @Override
  public Object getPrincipal() {
    return loginAccount;
  }

  @Override
  public Object getCredentials() {
    return token;
  }
}

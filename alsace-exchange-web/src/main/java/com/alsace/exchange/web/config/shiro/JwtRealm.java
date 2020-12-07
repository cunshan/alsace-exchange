package com.alsace.exchange.web.config.shiro;

import com.alsace.exchange.service.user.domain.User;
import com.alsace.exchange.service.user.service.UserService;
import com.alsace.exchange.web.common.WebConstants;
import com.alsace.exchange.web.config.exception.JwtException;
import com.alsace.exchange.web.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class JwtRealm extends AuthorizingRealm {

  @Resource
  private UserService userService;

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof BearerToken;
  }

  /**
   * 获取权限
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    return null;
  }

  /**
   * 认证
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
    BearerToken token = (BearerToken) authenticationToken;
    String loginAccount = JwtUtils.getLoginAccount(token.getToken());
    User userParam = new User();
    userParam.setLoginAccount(loginAccount).setDeleted(false);
    User user = userService.findOne(userParam);
    //获取用户为空
    if (user == null) {
      throw new JwtException("用户不存在！");
    }
    //获取用户已经锁定
    if (user.getLocked()) {
      throw new JwtException("用户已经被锁定！");
    }
    //校验token是否过期
    if(!JwtUtils.verify(token.getToken(),user.getLoginAccount(),user.getPassword())){
      throw new JwtException(WebConstants.JWT_ERROR_MSG);
    }
    return new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
  }


}

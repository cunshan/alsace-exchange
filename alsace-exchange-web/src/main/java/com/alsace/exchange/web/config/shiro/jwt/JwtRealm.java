package com.alsace.exchange.web.config.shiro.jwt;

import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.service.UserService;
import com.alsace.exchange.web.common.WebConstants;
import com.alsace.exchange.web.config.exception.JwtException;
import com.alsace.exchange.web.utils.JwtUtils;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

@Slf4j
public class JwtRealm extends AuthorizingRealm {

  @Resource
  private UserService userService;

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof JwtToken;
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
    JwtToken token = (JwtToken) authenticationToken;
    if (StringUtils.isEmpty(token.getLoginAccount())) {
      throw new JwtException("非法TOKEN！");
    }
    User userParam = new User();
    userParam.setLoginAccount(token.getLoginAccount()).setDeleted(false);
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
    try{
      if(!JwtUtils.verify(token.getToken(),user.getLoginAccount(),user.getUserName(),user.getPassword())){
        throw new JwtException(WebConstants.JWT_ERROR_MSG);
      }
    }catch (TokenExpiredException ex){
      //TODO 原token过期，刷新操作 后面完善
      throw new JwtException(WebConstants.JWT_ERROR_MSG);
    }
    return new SimpleAuthenticationInfo(token, token.getCredentials(), getName());
  }


}

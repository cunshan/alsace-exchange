package com.alsace.exchange.web.config.shiro;

import com.alsace.exchange.common.base.LoginInfoProvider;
import com.alsace.exchange.web.utils.LoginUtils;
import org.springframework.stereotype.Component;

@Component
public class ShiroLoginInfoProvider implements LoginInfoProvider {
  @Override
  public String loginAccount() {
    return LoginUtils.loginAccount();
  }

  @Override
  public String userName() {
    return LoginUtils.userName();
  }
}

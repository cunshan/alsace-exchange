package com.alsace.exchange.web.sys.biz;

import com.alsace.exchange.service.sys.service.SmsSendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class LoginHandler {

  private static final String CHECK_CODE_TEMPLATE = "移动检测平台】您的登录验证码是：%s.";

  @Resource
  private SmsSendService smsSendService;

  /**
   * 发送验证码
   * @param mobile 手机号
   */
  public void sendSms(String mobile) {
    smsSendService.send(mobile,String.format(CHECK_CODE_TEMPLATE,"1234"));
  }
}

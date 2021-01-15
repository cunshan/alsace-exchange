package com.alsace.exchange.web.sys.biz;

import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.service.SmsSendService;
import com.alsace.exchange.service.sys.service.UserService;
import com.alsace.exchange.web.config.shiro.jwt.JwtToken;
import com.alsace.exchange.web.sys.vo.AppLoginVo;
import com.alsace.exchange.web.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.Assert;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class LoginHandler {

  private static final String CHECK_CODE_TEMPLATE = "移动检测平台】您的登录验证码是：%s.";
  private static final Random random = new Random();

  @Resource
  private SmsSendService smsSendService;
  @Resource
  private StringRedisTemplate stringRedisTemplate;
  @Resource
  private UserService userService;

  /**
   * 发送验证码
   *
   * @param mobile 手机号
   */
  public String sendCheck(String mobile) {
    StringBuilder checkSb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      checkSb.append(random.nextInt(9));
    }
    String checkCode = checkSb.toString();
    //验证码放入redis
    stringRedisTemplate.opsForValue().set(checkCode, mobile, 10, TimeUnit.MINUTES);
//    smsSendService.send(mobile,String.format(CHECK_CODE_TEMPLATE,checkCode.toString()));
    return checkCode;
  }

  /**
   * app登录逻辑
   *
   * @return 登录后的token
   */
  public String appLogin(AppLoginVo param) {
    String mobile = stringRedisTemplate.opsForValue().get(param.getCheckCode());
    Assert.state(param.getMobile().equalsIgnoreCase(mobile), "验证码失效！");
    User queryUser = new User();
    queryUser.setTel(mobile).setDeleted(false);
    User dbUser = userService.findOne(queryUser);
    org.springframework.util.Assert.state(dbUser != null, "用户不存在！");
    org.springframework.util.Assert.state(!dbUser.getLocked(), "用户已被锁定！");
    String tokenStr = JwtUtils.sign(dbUser.getLoginAccount(), dbUser.getUserName(), dbUser.getPassword(), 60);
    JwtToken token = new JwtToken(tokenStr);
    SecurityUtils.getSubject().login(token);
    //TODO 记录登录日志
    return tokenStr;
  }

  public String login(User param) {
    User userParam = new User().setLoginAccount(param.getLoginAccount());
    User user = userService.findOne(userParam);
    org.springframework.util.Assert.state(user != null, "用户不存在！");
    org.springframework.util.Assert.state(!user.getLocked(), "用户已被锁定！");
    String password = DigestUtils.md5Hex(param.getPassword() + param.getLoginAccount());
    org.springframework.util.Assert.state(password.equals(user.getPassword()), "密码错误！");
    //密码匹配  登录成功
    String tokenStr = JwtUtils.sign(user.getLoginAccount(), user.getUserName(), user.getPassword(), 60);
    JwtToken token = new JwtToken(tokenStr);
    SecurityUtils.getSubject().login(token);
    //TODO 记录登录日志
    return tokenStr;
  }
}

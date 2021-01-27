package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.service.sys.service.SmsSendService;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SmsSendServiceImpl implements SmsSendService {

  private static final String url =
      "http://m.5c.com.cn/api/send/index.php?username={username}&password_md5={password}&apikey={appKey}&mobile={mobile}&encode=UTF-8&content={content}";

  @Resource
  private RestTemplate restTemplate;

  @Value("${sms.username}")
  private String username;
  @Value("${sms.password}")
  private String password;
  @Value("${sms.appKey}")
  private String appKey;

  @Override
  public void send(String mobile, String msg) {
    try {
      Map<String, String> paramMap = new HashMap<>();
      String content = URLEncoder.encode(msg, Charsets.UTF_8.name());
      paramMap.put("content", content);
      String passwordMd5 = DigestUtils.md5Hex(password);
      paramMap.put("password", passwordMd5);
      paramMap.put("appKey", appKey);
      paramMap.put("username", username);
      paramMap.put("mobile", mobile);
      paramMap.put("content", msg);
      String resMsg = restTemplate.getForObject(url, String.class, paramMap);
      log.info("手机验证码返回结果：" + resMsg);
      boolean success = StringUtils.isNotBlank(resMsg) && resMsg.startsWith("success:");
      if (!success) {
        throw new AlsaceException("调用返回失败：" + resMsg);
      }
    } catch (UnsupportedEncodingException ex) {
      throw new AlsaceException(ex);
    }
  }
}

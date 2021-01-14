package com.alsace.exchange.service.sys.service;

/**
 * 发送短信服务
 */
public interface SmsSendService {

  void send(String mobile, String msg);

}

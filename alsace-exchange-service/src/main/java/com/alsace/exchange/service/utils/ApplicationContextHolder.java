package com.alsace.exchange.service.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;

public class ApplicationContextHolder implements ApplicationContextAware {

  private static ApplicationContext application;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    application = applicationContext;
  }

  /**
   * 获取对应bean
   */
  public static <T> T getBean(Class<T> tClass) {
    T bean = application == null ? null : application.getBean(tClass);
    Assert.state(bean != null, String.format("获取bean失败！%s", tClass.getName()));
    return bean;
  }

  public static void publishEvent(ApplicationEvent event) {
    application.publishEvent(event);
  }
}

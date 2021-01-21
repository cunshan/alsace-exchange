package com.alsace.exchange.web.config.log.listener;


import com.alsace.exchange.service.sys.domain.OperationLog;
import com.alsace.exchange.service.sys.service.OperationLogService;
import com.alsace.exchange.service.utils.ApplicationContextHolder;
import com.alsace.exchange.web.config.log.event.OperationLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OperationLogListener {


  @Async
  @EventListener(OperationLogEvent.class)
  public void saveOperationLog(OperationLogEvent event) {
    OperationLog operationLog = (OperationLog) event.getSource();
    Assert.notNull(operationLog, "日志对象为空！");
    OperationLogService operationLogService = ApplicationContextHolder.getBean(OperationLogService.class);
    operationLogService.save(operationLog);
  }

}

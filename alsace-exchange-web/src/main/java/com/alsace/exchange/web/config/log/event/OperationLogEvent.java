package com.alsace.exchange.web.config.log.event;

import com.alsace.exchange.service.sys.domain.OperationLog;
import org.springframework.context.ApplicationEvent;


public class OperationLogEvent extends ApplicationEvent {
  private static final long serialVersionUID = 6380884507260472429L;


  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with
   *               which the event is associated (never {@code null})
   */
  public OperationLogEvent(OperationLog source) {
    super(source);
  }
}

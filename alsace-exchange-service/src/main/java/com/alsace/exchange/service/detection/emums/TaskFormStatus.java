package com.alsace.exchange.service.detection.emums;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 人员检测任务表单状态
 * 10：进行中 20：已完成
 */
@Getter
@Accessors(fluent = true)
public enum TaskFormStatus {

  PROCESSING(10, "进行中"),
  COMPLETED(20, "已完成");

  private final Integer status;

  private final String desc;


  TaskFormStatus(Integer status, String desc) {
    this.status = status;
    this.desc = desc;
  }

}

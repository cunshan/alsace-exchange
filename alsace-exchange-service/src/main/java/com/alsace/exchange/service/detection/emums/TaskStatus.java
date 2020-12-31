package com.alsace.exchange.service.detection.emums;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 人员检测任务状态
 * 10：已创建 20：待下发 30：已下发 40：待开始 50：进行中 60：已完成 70：已取消
 */
@Getter
@Accessors(fluent = true)
public enum TaskStatus {

  INIT(10, "已创建"),
  ASSIGNING(20, "待下发"),
  ASSIGNED(30, "已下发"),
  READY(40, "待开始"),
  PROCESSING(50, "进行中"),
  COMPLETED(60, "已完成"),
  CANCELED(70, "已取消");

  private final Integer status;

  private final String desc;

  TaskStatus(Integer status, String desc) {
    this.status = status;
    this.desc = desc;
  }

}

package com.alsace.exchange.service.detection.emums;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 人员检测任务状态
 * 10：已创建 20：待下发 30：已下发 40：待开始 50：进行中 60：已完成 70：已取消
 */
@Getter
@Accessors(fluent = true)
public enum TaskDetailStatus {

  INIT(10, "已创建"),
  SUBMITTED(20, "已提交");

  private final Integer status;

  private final String desc;


  TaskDetailStatus(Integer status, String desc) {
    this.status = status;
    this.desc = desc;
  }

  public static String getDesc(Integer taskStatus) {
    String desc = "";
    for (int i = 0; i < values().length; i++) {
      TaskDetailStatus status = values()[i];
      if (status.status.equals(taskStatus)) {
        desc = status.desc();
        break;
      }
    }
    return desc;
  }
}

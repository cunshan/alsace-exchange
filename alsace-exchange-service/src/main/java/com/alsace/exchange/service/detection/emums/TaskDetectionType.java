package com.alsace.exchange.service.detection.emums;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum TaskDetectionType {

  ALL(1, "全民检测"),
  NOT_ALL(2, "非全民检测");

  private final Integer status;

  private final String desc;

  TaskDetectionType(Integer status, String desc) {
    this.status = status;
    this.desc = desc;
  }

  public static String getDesc(Integer taskStatus) {
    String desc = "";
    for (int i = 0; i < values().length; i++) {
      TaskDetectionType status = values()[i];
      if (status.status.equals(taskStatus)) {
        desc = status.desc();
        break;
      }
    }
    return desc;
  }
}

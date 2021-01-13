package com.alsace.exchange.service.detection.emums;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum EnvironmentTaskDetailResultType {

  FOOD(10, "食物"),
  OTHER(20, "其他");

  private final Integer status;

  private final String desc;


  EnvironmentTaskDetailResultType(Integer status, String desc) {
    this.status = status;
    this.desc = desc;
  }

  public static String getDesc(Integer taskStatus) {
    String desc = "";
    for (int i = 0; i < values().length; i++) {
      EnvironmentTaskDetailResultType status = values()[i];
      if (status.status.equals(taskStatus)) {
        desc = status.desc();
        break;
      }
    }
    return desc;
  }

}

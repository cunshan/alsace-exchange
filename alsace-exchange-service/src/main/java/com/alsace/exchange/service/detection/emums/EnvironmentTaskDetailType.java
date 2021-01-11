package com.alsace.exchange.service.detection.emums;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum EnvironmentTaskDetailType {

  INIT(10, "已创建"),
  SUBMITTED(20, "已提交");

  private final Integer status;

  private final String desc;


  EnvironmentTaskDetailType(Integer status, String desc) {
    this.status = status;
    this.desc = desc;
  }

  public static String getDesc(Integer taskStatus) {
    String desc = "";
    for (int i = 0; i < values().length; i++) {
      EnvironmentTaskDetailType status = values()[i];
      if (status.status.equals(taskStatus)) {
        desc = status.desc();
        break;
      }
    }
    return desc;
  }

}

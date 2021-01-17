package com.alsace.exchange.service.detection.emums;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public enum PersonTaskDetectionMethod {

  NUCLEIC_ACID(1, "核酸检测"),
  ANTIBODY(2, "抗体检测"),
  BOTH(3, "核酸和抗体检测");

  private final Integer status;

  private final String desc;

  PersonTaskDetectionMethod(Integer status, String desc) {
    this.status = status;
    this.desc = desc;
  }

  public static String getDesc(Integer taskStatus) {
    String desc = "";
    for (int i = 0; i < values().length; i++) {
      PersonTaskDetectionMethod status = values()[i];
      if (status.status.equals(taskStatus)) {
        desc = status.desc();
        break;
      }
    }
    return desc;
  }
}

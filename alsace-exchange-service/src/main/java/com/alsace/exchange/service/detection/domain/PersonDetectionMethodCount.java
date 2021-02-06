package com.alsace.exchange.service.detection.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonDetectionMethodCount implements Serializable {

  private static final long serialVersionUID = 899305329167592424L;

  private String detectionMethod;

  private Integer count;
}

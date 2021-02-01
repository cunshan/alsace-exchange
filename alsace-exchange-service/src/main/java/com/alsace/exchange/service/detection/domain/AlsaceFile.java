package com.alsace.exchange.service.detection.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlsaceFile implements Serializable {

  private static final long serialVersionUID = -5866486124983519560L;

  private String filePath;//下载路径
  private String fileName;//文件名称
  private byte[] bytes;
  private String name;


}

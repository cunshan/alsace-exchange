package com.alsace.exchange.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CodeName implements Serializable {
  private static final long serialVersionUID = 7908473555079186483L;

  private String code;

  private String name;

}

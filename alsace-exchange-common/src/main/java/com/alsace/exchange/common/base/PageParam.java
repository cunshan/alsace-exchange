package com.alsace.exchange.common.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageParam<T> implements Serializable {
  private static final long serialVersionUID = -558473803199401946L;

  private int pageNum = 1;

  private int pageSize = 10;

  private T param;

}

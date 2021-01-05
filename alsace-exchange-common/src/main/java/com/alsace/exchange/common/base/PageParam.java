package com.alsace.exchange.common.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class PageParam<T> implements Serializable {
  private static final long serialVersionUID = -558473803199401946L;

  private int pageNum = 1;

  private int pageSize = 10;

  private T param;

}

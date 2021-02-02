package com.alsace.exchange.common.base;

import com.alsace.exchange.common.enums.OrderByEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class AlsaceOrderBy implements Serializable {

  private static final long serialVersionUID = -8959185490386305107L;

  /**
   * 排序顺序
   */
  private OrderByEnum order;

  /**
   * 字段名称
   */
  private List<String> propertyList;

}

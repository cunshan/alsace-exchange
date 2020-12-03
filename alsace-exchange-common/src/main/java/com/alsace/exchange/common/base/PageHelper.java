package com.alsace.exchange.common.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * 分页助手
 */
public class PageHelper {

  /**
   * 获取分页对象
   * JPA分页是从0开始的，页面传进的值是1，这里做一个减1的处理
   */
  public static <T> Pageable page(PageParam<T> param) {
    return PageRequest.of(param.getPageNum()-1,param.getPageSize());
  }


}

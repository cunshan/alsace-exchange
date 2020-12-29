package com.alsace.exchange.common.base;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
public class TreeVo<T> {

  private T value;

  private List<TreeVo<T>> children;

  public TreeVo(T value){
    this.value = value;
  }

}

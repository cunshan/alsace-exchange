package com.alsace.exchange.common.utils;

/**
 * ID工具
 */
public class IdUtils {

  private static final Sequence sequence = new Sequence();

  /**
   * 雪花算法获取ID
   */
  public static Long id(){
    return sequence.nextId();
  }

}

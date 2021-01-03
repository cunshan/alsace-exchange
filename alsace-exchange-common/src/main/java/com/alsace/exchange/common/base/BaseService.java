package com.alsace.exchange.common.base;

import java.util.List;

public interface BaseService<T, K> {

  /**
   * 根据对象精确匹配
   */
  T findOne(T t);

  /**
   * 根据ID查询
   */
  T getOneById(K id);

  /**
   * 保存
   */
  T save(T param);

  /**
   * 批量保存
   */
  List<T> saveBatch(List<T> param);

  /**
   * 精确匹配，获取分页查询
   */
  PageResult<T> findPage(PageParam<T> param);

  /**
   * 更新
   */
  T update(T param);

  /**
   * 删除
   * @param id
   */
  boolean delete(List<Long> id);

  /**
   * 获取所有记录
   */
  List<T> findAll(T param);
}

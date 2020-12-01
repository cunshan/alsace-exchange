package com.alsace.exchange.common.base;

public interface BaseService<T,K> {

    /**
     * 根据对象精确匹配
     */
    T findOne(T t);

    T getOneById(K id);

}

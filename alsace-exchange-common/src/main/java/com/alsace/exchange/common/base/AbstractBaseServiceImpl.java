package com.alsace.exchange.common.base;

import com.alsace.exchange.common.constants.Constants;

import java.util.List;

public class AbstractBaseServiceImpl<T,K> implements BaseService<T,K> {
  @Override
  public T findOne(T t) {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
  }

  @Override
  public T getOneById(K id) {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
  }

  @Override
  public T save(T param) {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
  }

  @Override
  public List<T> saveBatch(List<T> param) {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
  }

  @Override
  public PageResult<T> findPage(PageParam<T> param) {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
  }

  @Override
  public T update(T param) {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
  }

  @Override
  public boolean delete(K id) {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
  }
}

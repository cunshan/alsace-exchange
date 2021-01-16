package com.alsace.exchange.service.sys.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.sys.domain.UserData;

public interface UserDataService extends BaseService<UserData,Long> {

  /**
   * 根据账号删除所有
   */
  void deleteByLoginAccount(String loginAccount);
}

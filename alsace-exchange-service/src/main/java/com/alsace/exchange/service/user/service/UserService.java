package com.alsace.exchange.service.user.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.user.domain.User;
import com.alsace.exchange.service.user.domain.UserRole;

import java.util.List;

public interface UserService extends BaseService<User,Long> {

  /**
   * 为用户增加角色
   * @param loginAccount 用户登录名
   * @param roleList 角色编码
   * @return 是否成功
   */
  boolean addUserRoles(String loginAccount, List<String> roleList);

}

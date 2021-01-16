package com.alsace.exchange.service.sys.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.domain.UserData;

import java.util.List;

public interface UserService extends BaseService<User,Long> {

  /**
   * 为用户增加角色
   * @param loginAccount 用户登录名
   * @param roleList 角色编码
   * @return 是否成功
   */
  boolean addUserRoles(String loginAccount, List<String> roleList);

  /**
   * 导入用户信息
   * @param param
   * @return
   */
  List<User> importUser(List<Object> param);

  /**
   * 根据账号列表查询用户信息
   */
  List<User> findAllByLoginAccounts(List<String> accountList);

  /**
   * 为用户增加用户权限
   * @param loginAccount 登录账号
   * @param dataList 数据权限列表
   */
  boolean addUserData(String loginAccount, List<UserData> dataList);
}

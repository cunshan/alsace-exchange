package com.alsace.exchange.service.sys.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.sys.domain.Role;

import java.util.List;

public interface RoleService extends BaseService<Role,Long> {


  /**
   * 为角色添加菜单
   */
  boolean addMenus(String roleCode, List<Long> menuIdList);

  /**
   * 根据登录账号查询用户菜单
   * @param loginAccount 登录账号
   */
  List<Role> findRoleByLoginAccount(String loginAccount);
}

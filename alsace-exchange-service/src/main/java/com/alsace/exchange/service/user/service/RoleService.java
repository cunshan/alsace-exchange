package com.alsace.exchange.service.user.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.user.domain.Role;

import java.util.List;

public interface RoleService extends BaseService<Role,Long> {


  /**
   * 为角色添加菜单
   */
  boolean addMenus(String roleCode, List<Long> menuIdList);
}

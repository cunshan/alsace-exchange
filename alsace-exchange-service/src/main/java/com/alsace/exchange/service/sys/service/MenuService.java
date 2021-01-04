package com.alsace.exchange.service.sys.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.sys.domain.Menu;

import java.util.List;

public interface MenuService extends BaseService<Menu,Long> {

  /**
   * 根据角色编码查询菜单
   */
  List<Menu> findByRoleCode(String roleCode);
}

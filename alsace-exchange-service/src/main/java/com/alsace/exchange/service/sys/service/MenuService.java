package com.alsace.exchange.service.sys.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.sys.domain.Menu;

import java.util.List;

public interface MenuService extends BaseService<Menu,Long> {

  /**
   * 根据角色编码查询菜单
   */
  List<Menu> findByRoleCode(List<String> roleCodeList);

  /**
   * 根据登录账号查询菜单
   * @param loginAccount 登录账号
   */
  List<Menu> findByLoginAccount(String loginAccount);
}

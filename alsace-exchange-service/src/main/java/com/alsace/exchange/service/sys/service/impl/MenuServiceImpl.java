package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.Menu;
import com.alsace.exchange.service.sys.domain.Role;
import com.alsace.exchange.service.sys.domain.UserRole;
import com.alsace.exchange.service.sys.repositories.MenuRepository;
import com.alsace.exchange.service.sys.repositories.UserRoleRepository;
import com.alsace.exchange.service.sys.service.MenuService;
import com.alsace.exchange.service.sys.service.RoleService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends AbstractBaseServiceImpl<Menu> implements MenuService {

  @Resource
  private MenuRepository menuRepository;

  @Resource
  private RoleService roleService;

  @Override
  protected JpaRepository<Menu, Long> getJpaRepository() {
    return this.menuRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Menu> getJpaSpecificationExecutor() {
    return this.menuRepository;
  }

  @Override
  public List<Menu> findByRoleCode(List<String> roleCodeList) {
    return this.menuRepository.findByRoleCode(roleCodeList);
  }

  @Override
  public List<Menu> findByLoginAccount(String loginAccount) {
    List<Role> roleList = roleService.findRoleByLoginAccount(loginAccount);
    List<String> roleCodeList = new ArrayList<>(roleList.size());
    roleList.forEach(role -> roleCodeList.add(role.getRoleCode()));
    return this.findByRoleCode(roleCodeList);
  }
}

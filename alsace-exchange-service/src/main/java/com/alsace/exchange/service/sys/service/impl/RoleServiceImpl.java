package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.service.sys.domain.Role;
import com.alsace.exchange.service.sys.domain.RoleMenu;
import com.alsace.exchange.service.sys.repositories.RoleMenuRepository;
import com.alsace.exchange.service.sys.repositories.RoleRepository;
import com.alsace.exchange.service.sys.service.RoleService;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends AbstractBaseServiceImpl<Role> implements RoleService {

  @Resource
  private RoleRepository roleRepository;
  @Resource
  private RoleMenuRepository roleMenuRepository;

  @Override
  protected JpaRepository<Role, Long> getJpaRepository() {
    return this.roleRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Role> getJpaSpecificationExecutor() {
    return this.roleRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean addMenus(String roleCode, List<Long> menuIdList) {
    Assert.hasLength(roleCode, "角色编码为空！");
    Assert.notEmpty(menuIdList, "菜单列表为空！");
    Role roleParam = new Role();
    roleParam.setRoleCode(roleCode)
        .setDeleted(false);
    roleRepository.findOne(Example.of(roleParam)).orElseThrow(() -> new AlsaceException(String.format("角色【%s】不存在！", roleCode)));
    //删除角色所有菜单
    roleMenuRepository.deleteAllByRoleCode(roleCode);
    menuIdList = menuIdList.stream().distinct().collect(Collectors.toList());
    List<RoleMenu> roleMenuList = new ArrayList<>(menuIdList.size());
    menuIdList.forEach(menuId->{
      RoleMenu rm = new RoleMenu(roleCode,menuId);
      rm.setId(IdUtils.id())
          .setDeleted(false)
          .setCreatedBy(getLoginAccount())
          .setCreatedDate(new Date());
      roleMenuList.add(rm);
    });
    roleMenuRepository.saveAll(roleMenuList);
    return true;
  }

  @Override
  public List<Role> findRoleByLoginAccount(String loginAccount) {
    Assert.hasLength(loginAccount,"登录账号为空！");
    return roleRepository.findByLoginAccount(loginAccount);
  }

}

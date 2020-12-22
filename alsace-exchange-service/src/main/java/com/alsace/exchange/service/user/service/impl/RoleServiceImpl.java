package com.alsace.exchange.service.user.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.service.user.domain.Role;
import com.alsace.exchange.service.user.domain.RoleMenu;
import com.alsace.exchange.service.user.repositories.RoleMenuRepository;
import com.alsace.exchange.service.user.repositories.RoleRepository;
import com.alsace.exchange.service.user.service.RoleService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
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


  @PostConstruct
  protected void init() {
    super.jpaRepository = roleRepository;
    super.specificationExecutor = roleRepository;
  }

  @Override
  public boolean addMenus(String roleCode, List<Long> menuIdList) {
    Assert.hasLength(roleCode, "角色编码为空！");
    Assert.notEmpty(menuIdList, "菜单列表为空！");
    Role roleParam = new Role();
    roleParam.setRoleCode(roleCode)
        .setDeleted(false);
    roleRepository.findOne(Example.of(roleParam)).orElseThrow(() -> new AlsaceException(String.format("角色【%s】不存在！", roleCode)));
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
}

package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.Menu;
import com.alsace.exchange.service.sys.repositories.MenuRepository;
import com.alsace.exchange.service.sys.service.MenuService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl extends AbstractBaseServiceImpl<Menu> implements MenuService {

  @Resource
  private MenuRepository menuRepository;



  @Override
  protected JpaRepository<Menu, Long> getJpaRepository() {
    return this.menuRepository;
  }

  @Override
  protected JpaSpecificationExecutor<Menu> getJpaSpecificationExecutor() {
    return this.menuRepository;
  }

  @Override
  public List<Menu> findByRoleCode(String roleCode) {
    return this.menuRepository.findByRoleCode(roleCode);
  }
}

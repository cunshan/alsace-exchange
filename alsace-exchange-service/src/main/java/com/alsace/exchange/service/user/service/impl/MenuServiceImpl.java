package com.alsace.exchange.service.user.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.user.domain.Menu;
import com.alsace.exchange.service.user.repositories.MenuRepository;
import com.alsace.exchange.service.user.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class MenuServiceImpl extends AbstractBaseServiceImpl<Menu> implements MenuService {

  @Resource
  private MenuRepository menuRepository;


  @PostConstruct
  protected void init(){
    super.jpaRepository = menuRepository;
    super.specificationExecutor = menuRepository;
  }

}

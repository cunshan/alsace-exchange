package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.Menu;
import com.alsace.exchange.service.sys.repositories.MenuRepository;
import com.alsace.exchange.service.sys.service.MenuService;
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

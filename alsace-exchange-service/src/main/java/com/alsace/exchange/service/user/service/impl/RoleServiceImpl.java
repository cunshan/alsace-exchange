package com.alsace.exchange.service.user.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.user.domain.Role;
import com.alsace.exchange.service.user.repositories.RoleRepository;
import com.alsace.exchange.service.user.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class RoleServiceImpl extends AbstractBaseServiceImpl<Role> implements RoleService {

  @Resource
  private RoleRepository roleRepository;


  @PostConstruct
  protected void init(){
    super.jpaRepository = roleRepository;
    super.specificationExecutor = roleRepository;
  }

}

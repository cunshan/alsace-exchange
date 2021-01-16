package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.UserData;
import com.alsace.exchange.service.sys.repositories.UserDataRepository;
import com.alsace.exchange.service.sys.service.UserDataService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserDataServiceImpl extends AbstractBaseServiceImpl<UserData> implements UserDataService {

  @Resource
  private UserDataRepository userDataRepository;


  @Override
  protected JpaRepository<UserData, Long> getJpaRepository() {
    return this.userDataRepository;
  }

  @Override
  protected JpaSpecificationExecutor<UserData> getJpaSpecificationExecutor() {
    return this.userDataRepository;
  }
}

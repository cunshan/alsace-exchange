package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.DictValue;
import com.alsace.exchange.service.sys.repositories.DictValueRepository;
import com.alsace.exchange.service.sys.service.DictValueService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DictValueServiceImpl extends AbstractBaseServiceImpl<DictValue> implements DictValueService {

  @Resource
  private DictValueRepository dictValueRepository;


  @Override
  protected JpaRepository<DictValue, Long> getJpaRepository() {
    return this.dictValueRepository;
  }

  @Override
  protected JpaSpecificationExecutor<DictValue> getJpaSpecificationExecutor() {
    return this.dictValueRepository;
  }
}

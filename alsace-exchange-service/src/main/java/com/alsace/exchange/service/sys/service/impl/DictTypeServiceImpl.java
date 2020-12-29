package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.DictType;
import com.alsace.exchange.service.sys.repositories.DictTypeRepository;
import com.alsace.exchange.service.sys.service.DictTypeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DictTypeServiceImpl extends AbstractBaseServiceImpl<DictType> implements DictTypeService {

  @Resource
  private DictTypeRepository dictTypeRepository;


  @Override
  protected JpaRepository<DictType, Long> getJpaRepository() {
    return this.dictTypeRepository;
  }

  @Override
  protected JpaSpecificationExecutor<DictType> getJpaSpecificationExecutor() {
    return this.dictTypeRepository;
  }
}

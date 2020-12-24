package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.DictType;
import com.alsace.exchange.service.sys.repositories.DictTypeRepository;
import com.alsace.exchange.service.sys.service.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class DictTypeServiceImpl extends AbstractBaseServiceImpl<DictType> implements DictTypeService {

  @Resource
  private DictTypeRepository dictTypeRepository;

  @PostConstruct
  protected void init() {
    super.jpaRepository = dictTypeRepository;
    super.specificationExecutor = dictTypeRepository;
  }

}

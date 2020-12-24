package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.DictValue;
import com.alsace.exchange.service.sys.repositories.DictValueRepository;
import com.alsace.exchange.service.sys.service.DictValueService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class DictValueServiceImpl extends AbstractBaseServiceImpl<DictValue> implements DictValueService {

  @Resource
  private DictValueRepository dictValueRepository;

  @PostConstruct
  protected void init() {
    super.jpaRepository = dictValueRepository;
    super.specificationExecutor = dictValueRepository;
  }

}

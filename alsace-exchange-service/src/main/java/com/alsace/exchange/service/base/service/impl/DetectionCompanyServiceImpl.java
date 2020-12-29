package com.alsace.exchange.service.base.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.base.domain.DetectionCompany;
import com.alsace.exchange.service.base.repositories.DetectionCompanyRepositories;
import com.alsace.exchange.service.base.service.DetectionCompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class DetectionCompanyServiceImpl extends AbstractBaseServiceImpl<DetectionCompany> implements DetectionCompanyService {

  @Resource
  private DetectionCompanyRepositories detectionCompanyRepositories;

  @PostConstruct
  public void init(){
    this.jpaRepository = detectionCompanyRepositories;
    this.specificationExecutor = detectionCompanyRepositories;
  }

}

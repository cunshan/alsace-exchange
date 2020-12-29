package com.alsace.exchange.service.base.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.base.domain.DetectionOrg;
import com.alsace.exchange.service.base.repositories.DetectionOrgRepositories;
import com.alsace.exchange.service.base.service.DetectionOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class DetectionOrgServiceImpl extends AbstractBaseServiceImpl<DetectionOrg> implements DetectionOrgService {

  @Resource
  private DetectionOrgRepositories detectionOrgRepositories;

  @PostConstruct
  public void init(){
    this.jpaRepository = detectionOrgRepositories;
    this.specificationExecutor = detectionOrgRepositories;
  }

}

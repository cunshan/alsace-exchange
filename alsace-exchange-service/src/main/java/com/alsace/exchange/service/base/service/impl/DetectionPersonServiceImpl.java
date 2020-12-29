package com.alsace.exchange.service.base.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.base.domain.DetectionPerson;
import com.alsace.exchange.service.base.repositories.DetectionPersonRepositories;
import com.alsace.exchange.service.base.service.DetectionPersonService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class DetectionPersonServiceImpl extends AbstractBaseServiceImpl<DetectionPerson> implements DetectionPersonService {

  @Resource
  private DetectionPersonRepositories detectionPersonRepositories;

  @PostConstruct
  public void init(){
    super.jpaRepository = detectionPersonRepositories;
    super.specificationExecutor = detectionPersonRepositories;
  }

}

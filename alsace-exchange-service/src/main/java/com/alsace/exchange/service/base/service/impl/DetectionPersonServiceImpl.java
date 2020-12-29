package com.alsace.exchange.service.base.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.base.domain.DetectionPerson;
import com.alsace.exchange.service.base.repositories.DetectionPersonRepositories;
import com.alsace.exchange.service.base.service.DetectionPersonService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DetectionPersonServiceImpl extends AbstractBaseServiceImpl<DetectionPerson> implements DetectionPersonService {

  @Resource
  private DetectionPersonRepositories detectionPersonRepositories;


  @Override
  protected JpaRepository<DetectionPerson, Long> getJpaRepository() {
    return this.detectionPersonRepositories;
  }

  @Override
  protected JpaSpecificationExecutor<DetectionPerson> getJpaSpecificationExecutor() {
    return this.detectionPersonRepositories;
  }
}

package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.DetectionPerson;
import com.alsace.exchange.service.detection.repositories.DetectionPersonRepository;
import com.alsace.exchange.service.detection.service.DetectionPersonService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DetectionPersonServiceImpl extends AbstractBaseServiceImpl<DetectionPerson> implements DetectionPersonService {

  @Resource
  private DetectionPersonRepository detectionPersonRepository;


  @Override
  protected JpaRepository<DetectionPerson, Long> getJpaRepository() {
    return this.detectionPersonRepository;
  }

  @Override
  protected JpaSpecificationExecutor<DetectionPerson> getJpaSpecificationExecutor() {
    return this.detectionPersonRepository;
  }
}

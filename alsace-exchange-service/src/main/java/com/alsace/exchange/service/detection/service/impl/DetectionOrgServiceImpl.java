package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.DetectionOrg;
import com.alsace.exchange.service.detection.repositories.DetectionOrgRepository;
import com.alsace.exchange.service.detection.service.DetectionOrgService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DetectionOrgServiceImpl extends AbstractBaseServiceImpl<DetectionOrg> implements DetectionOrgService {

  @Resource
  private DetectionOrgRepository detectionOrgRepository;


  @Override
  protected JpaRepository<DetectionOrg, Long> getJpaRepository() {
    return this.detectionOrgRepository;
  }

  @Override
  protected JpaSpecificationExecutor<DetectionOrg> getJpaSpecificationExecutor() {
    return this.detectionOrgRepository;
  }
}

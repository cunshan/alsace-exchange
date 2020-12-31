package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.DetectionCompany;
import com.alsace.exchange.service.detection.repositories.DetectionCompanyRepository;
import com.alsace.exchange.service.detection.service.DetectionCompanyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DetectionCompanyServiceImpl extends AbstractBaseServiceImpl<DetectionCompany> implements DetectionCompanyService {

  @Resource
  private DetectionCompanyRepository detectionCompanyRepository;


  @Override
  protected JpaRepository<DetectionCompany, Long> getJpaRepository() {
    return this.detectionCompanyRepository;
  }

  @Override
  protected JpaSpecificationExecutor<DetectionCompany> getJpaSpecificationExecutor() {
    return this.detectionCompanyRepository;
  }
}

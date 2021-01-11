package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOperator;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskOperatorRepository;
import com.alsace.exchange.service.detection.service.EnvironmentTaskOperatorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EnvironmentTaskOperatorServiceImpl extends AbstractBaseServiceImpl<EnvironmentTaskOperator> implements EnvironmentTaskOperatorService {

  @Resource
  private EnvironmentTaskOperatorRepository environmentTaskOperatorRepository;


  @Override
  protected JpaRepository<EnvironmentTaskOperator, Long> getJpaRepository() {
    return this.environmentTaskOperatorRepository;
  }

  @Override
  protected JpaSpecificationExecutor<EnvironmentTaskOperator> getJpaSpecificationExecutor() {
    return this.environmentTaskOperatorRepository;
  }
}

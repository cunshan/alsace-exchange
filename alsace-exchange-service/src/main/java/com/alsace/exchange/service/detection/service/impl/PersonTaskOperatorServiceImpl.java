package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTaskOperator;
import com.alsace.exchange.service.detection.repositories.PersonTaskOperatorRepositories;
import com.alsace.exchange.service.detection.service.PersonTaskOperatorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonTaskOperatorServiceImpl extends AbstractBaseServiceImpl<PersonTaskOperator> implements PersonTaskOperatorService {

  @Resource
  private PersonTaskOperatorRepositories personTaskOperatorRepositories;


  @Override
  protected JpaRepository<PersonTaskOperator, Long> getJpaRepository() {
    return this.personTaskOperatorRepositories;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskOperator> getJpaSpecificationExecutor() {
    return this.personTaskOperatorRepositories;
  }
}

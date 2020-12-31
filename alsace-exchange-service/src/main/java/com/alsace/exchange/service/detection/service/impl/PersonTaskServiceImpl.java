package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.repositories.PersonTaskRepositories;
import com.alsace.exchange.service.detection.service.PersonTaskService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonTaskServiceImpl extends AbstractBaseServiceImpl<PersonTask> implements PersonTaskService {

  @Resource
  private PersonTaskRepositories personTaskRepositories;


  @Override
  protected JpaRepository<PersonTask, Long> getJpaRepository() {
    return this.personTaskRepositories;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTask> getJpaSpecificationExecutor() {
    return this.personTaskRepositories;
  }
}

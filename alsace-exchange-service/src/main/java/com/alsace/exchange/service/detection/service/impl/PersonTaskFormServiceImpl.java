package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import com.alsace.exchange.service.detection.repositories.PersonTaskFormRepository;
import com.alsace.exchange.service.detection.service.PersonTaskFormService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonTaskFormServiceImpl extends AbstractBaseServiceImpl<PersonTaskForm> implements PersonTaskFormService {

  @Resource
  private PersonTaskFormRepository personTaskDetailRepository;


  @Override
  protected JpaRepository<PersonTaskForm, Long> getJpaRepository() {
    return this.personTaskDetailRepository;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskForm> getJpaSpecificationExecutor() {
    return this.personTaskDetailRepository;
  }
}

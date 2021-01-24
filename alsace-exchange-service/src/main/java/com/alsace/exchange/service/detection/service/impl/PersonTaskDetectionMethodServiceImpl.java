package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTaskDetectionMethod;
import com.alsace.exchange.service.detection.repositories.PersonTaskDetectionMethodRepository;
import com.alsace.exchange.service.detection.service.PersonTaskDetectionMethodService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonTaskDetectionMethodServiceImpl extends AbstractBaseServiceImpl<PersonTaskDetectionMethod> implements PersonTaskDetectionMethodService {

  @Resource
  private PersonTaskDetectionMethodRepository personTaskDetectionMethodRepository;


  @Override
  protected JpaRepository<PersonTaskDetectionMethod, Long> getJpaRepository() {
    return this.personTaskDetectionMethodRepository;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskDetectionMethod> getJpaSpecificationExecutor() {
    return this.personTaskDetectionMethodRepository;
  }

}

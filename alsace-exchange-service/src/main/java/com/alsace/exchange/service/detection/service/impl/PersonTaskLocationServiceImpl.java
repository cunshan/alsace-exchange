package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import com.alsace.exchange.service.detection.repositories.PersonTaskLocationRepository;
import com.alsace.exchange.service.detection.service.PersonTaskLocationService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonTaskLocationServiceImpl extends AbstractBaseServiceImpl<PersonTaskLocation> implements PersonTaskLocationService {

  @Resource
  private PersonTaskLocationRepository personTaskLocationRepository;


  @Override
  protected JpaRepository<PersonTaskLocation, Long> getJpaRepository() {
    return this.personTaskLocationRepository;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskLocation> getJpaSpecificationExecutor() {
    return this.personTaskLocationRepository;
  }
}

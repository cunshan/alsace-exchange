package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.repositories.PersonTaskDetailRepositories;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonTaskDetailServiceImpl extends AbstractBaseServiceImpl<PersonTaskDetail> implements PersonTaskDetailService {

  @Resource
  private PersonTaskDetailRepositories personTaskDetailRepositories;


  @Override
  protected JpaRepository<PersonTaskDetail, Long> getJpaRepository() {
    return this.personTaskDetailRepositories;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskDetail> getJpaSpecificationExecutor() {
    return this.personTaskDetailRepositories;
  }
}

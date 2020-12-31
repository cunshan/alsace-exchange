package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;
import com.alsace.exchange.service.detection.repositories.PersonTaskOrgRepository;
import com.alsace.exchange.service.detection.service.PersonTaskOrgService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonTaskOrgServiceImpl extends AbstractBaseServiceImpl<PersonTaskOrg> implements PersonTaskOrgService {

  @Resource
  private PersonTaskOrgRepository personTaskOrgRepository;


  @Override
  protected JpaRepository<PersonTaskOrg, Long> getJpaRepository() {
    return this.personTaskOrgRepository;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskOrg> getJpaSpecificationExecutor() {
    return this.personTaskOrgRepository;
  }
}

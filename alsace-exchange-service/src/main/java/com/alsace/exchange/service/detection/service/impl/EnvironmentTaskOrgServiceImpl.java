package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOrg;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskOrgRepository;
import com.alsace.exchange.service.detection.service.EnvironmentTaskOrgService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EnvironmentTaskOrgServiceImpl extends AbstractBaseServiceImpl<EnvironmentTaskOrg> implements EnvironmentTaskOrgService {

  @Resource
  private EnvironmentTaskOrgRepository environmentTaskOrgRepository;


  @Override
  protected JpaRepository<EnvironmentTaskOrg, Long> getJpaRepository() {
    return this.environmentTaskOrgRepository;
  }

  @Override
  protected JpaSpecificationExecutor<EnvironmentTaskOrg> getJpaSpecificationExecutor() {
    return this.environmentTaskOrgRepository;
  }

  @Override
  public void deleteByTaskCode(String taskCode) {
    environmentTaskOrgRepository.deleteAllByTaskCode(taskCode);
  }
}

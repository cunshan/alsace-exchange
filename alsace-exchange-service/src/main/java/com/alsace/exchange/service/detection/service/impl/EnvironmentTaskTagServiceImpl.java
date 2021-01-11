package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskTag;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskTagRepository;
import com.alsace.exchange.service.detection.service.EnvironmentTaskTagService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class EnvironmentTaskTagServiceImpl extends AbstractBaseServiceImpl<EnvironmentTaskTag> implements EnvironmentTaskTagService {

  @Resource
  private EnvironmentTaskTagRepository environmentTaskTagRepository;


  @Override
  protected JpaRepository<EnvironmentTaskTag, Long> getJpaRepository() {
    return this.environmentTaskTagRepository;
  }

  @Override
  protected JpaSpecificationExecutor<EnvironmentTaskTag> getJpaSpecificationExecutor() {
    return this.environmentTaskTagRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteByTaskCode(String taskCode) {
    environmentTaskTagRepository.deleteAllByTaskCode(taskCode);
  }
}

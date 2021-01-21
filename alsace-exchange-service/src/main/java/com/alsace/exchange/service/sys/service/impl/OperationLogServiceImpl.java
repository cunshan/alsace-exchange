package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.sys.domain.OperationLog;
import com.alsace.exchange.service.sys.repositories.OperationLogRepository;
import com.alsace.exchange.service.sys.service.OperationLogService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OperationLogServiceImpl extends AbstractBaseServiceImpl<OperationLog> implements OperationLogService {

  @Resource
  private OperationLogRepository operationLogRepository;


  @Override
  protected JpaRepository<OperationLog, Long> getJpaRepository() {
    return this.operationLogRepository;
  }

  @Override
  protected JpaSpecificationExecutor<OperationLog> getJpaSpecificationExecutor() {
    return this.operationLogRepository;
  }
}

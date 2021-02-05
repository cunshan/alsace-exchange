package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.base.AlsaceOrderBy;
import com.alsace.exchange.common.base.AlsacePageHelper;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.enums.OrderByEnum;
import com.alsace.exchange.common.utils.JpaHelper;
import com.alsace.exchange.service.sys.domain.OperationLog;
import com.alsace.exchange.service.sys.repositories.OperationLogRepository;
import com.alsace.exchange.service.sys.service.OperationLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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


  @Override
  public PageResult<OperationLog> findPage(PageParam<OperationLog> param) {
    Set<String> likeSet = new HashSet<>();
    likeSet.add("operation");
    likeSet.add("method");
    likeSet.add("params");
    likeSet.add("result");
    likeSet.add("moduleName");
    likeSet.add("requestUrl");
    likeSet.add("ip");
    Page<OperationLog> logPage = getJpaSpecificationExecutor().findAll(
        JpaHelper.buildConditions(param.getParam(),likeSet, new AlsaceOrderBy(OrderByEnum.DESC,Arrays.asList("createdDate","modifiedDate"))), AlsacePageHelper.page(param));
    return new PageResult<>(logPage);

  }
}

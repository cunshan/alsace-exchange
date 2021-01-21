package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOperator;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskOperatorRepository;
import com.alsace.exchange.service.detection.service.EnvironmentTaskOperatorService;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnvironmentTaskOperatorServiceImpl extends AbstractBaseServiceImpl<EnvironmentTaskOperator> implements EnvironmentTaskOperatorService {

  @Resource
  private EnvironmentTaskOperatorRepository environmentTaskOperatorRepository;
  @Resource
  private UserService userService;


  @Override
  protected JpaRepository<EnvironmentTaskOperator, Long> getJpaRepository() {
    return this.environmentTaskOperatorRepository;
  }

  @Override
  protected JpaSpecificationExecutor<EnvironmentTaskOperator> getJpaSpecificationExecutor() {
    return this.environmentTaskOperatorRepository;
  }

  @Override
  public List<User> findAllUserInfo(String taskCode, Long locationId) {
    EnvironmentTaskOperator queryOperator = new EnvironmentTaskOperator();
    queryOperator.setTaskCode(taskCode).setTagId(locationId).setDeleted(false);
    List<EnvironmentTaskOperator> operatorList = this.findAll(queryOperator);
    List<String> accountList = new ArrayList<>(operatorList.size());
    operatorList.forEach(operator->accountList.add(operator.getLoginAccount()));
    return userService.findAllByLoginAccounts(accountList);
  }
}

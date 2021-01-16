package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTaskOperator;
import com.alsace.exchange.service.detection.repositories.PersonTaskOperatorRepository;
import com.alsace.exchange.service.detection.service.PersonTaskOperatorService;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonTaskOperatorServiceImpl extends AbstractBaseServiceImpl<PersonTaskOperator> implements PersonTaskOperatorService {

  @Resource
  private PersonTaskOperatorRepository personTaskOperatorRepository;
  @Resource
  private UserService userService;


  @Override
  protected JpaRepository<PersonTaskOperator, Long> getJpaRepository() {
    return this.personTaskOperatorRepository;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskOperator> getJpaSpecificationExecutor() {
    return this.personTaskOperatorRepository;
  }

  @Override
  public List<User> findAllUserInfo(String taskCode, Long locationId) {
    PersonTaskOperator queryOperator = new PersonTaskOperator();
    queryOperator.setTaskCode(taskCode).setLocationId(locationId).setDeleted(false);
    List<PersonTaskOperator> operatorList = this.findAll(queryOperator);
    List<String> accountList = new ArrayList<>(operatorList.size());
    operatorList.forEach(operator->accountList.add(operator.getLoginAccount()));
    return userService.findAllByLoginAccounts(accountList);
  }
}

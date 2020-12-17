package com.alsace.exchange.service.user.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.service.user.domain.User;
import com.alsace.exchange.service.user.repositories.UserRepository;
import com.alsace.exchange.service.user.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class UserServiceImpl extends AbstractBaseServiceImpl<User> implements UserService {

  @Resource
  private UserRepository userRepository;

  @PostConstruct
  protected void init(){
    super.jpaRepository = userRepository;
    super.specificationExecutor = userRepository;
  }

  @Override
  @AutoFill(AutoFillType.CREATE)
  @Transactional
  public User save(User param) {
    User userParam = new User();
    userParam.setLoginAccount(param.getLoginAccount()).setDeleted(false);
    long userCount = userRepository.count(Example.of(userParam));
    Assert.state(userCount <= 0, String.format("用户名%s已经存在！", param.getLoginAccount()));
    param.setLocked(false);
    return userRepository.saveAndFlush(param);
  }

}

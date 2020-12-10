package com.alsace.exchange.service.user.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.base.PageHelper;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.service.user.domain.User;
import com.alsace.exchange.service.user.repositories.UserRepository;
import com.alsace.exchange.service.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends AbstractBaseServiceImpl<User, Long> implements UserService {

  @Resource
  private UserRepository userRepository;


  @Override
  public User findOne(User user) {
    Example<User> example = Example.of(user);
    return userRepository.findOne(example).orElse(null);
  }

  @Override
  public User getOneById(Long id) {
    Assert.notNull(id, Constants.ID_NOT_NULL_ERROR);
    return userRepository.findById(id).orElse(null);
  }

  @Override
  @AutoFill(AutoFillType.CREATE)
  @Transactional
  public User save(User param) {
    User userParam = new User();
    userParam.setLoginAccount(param.getLoginAccount()).setDeleted(false);
    long userCount = userRepository.count(Example.of(userParam));
    Assert.state(userCount > 0, String.format("用户名%s已经存在！", param.getLoginAccount()));
    param.setLocked(false);
    return userRepository.saveAndFlush(param);
  }

  @Override
  @AutoFill(AutoFillType.UPDATE)
  @Transactional
  public User update(User param) {
    User dbUser = this.getOneById(param.getId());
    Assert.state(dbUser != null, Constants.UPDATE_NOT_EXISTS_ERROR);
    BeanUtils.copyProperties(param, dbUser);
    return userRepository.saveAndFlush(dbUser);
  }

  @Override
  public PageResult<User> findPage(PageParam<User> param) {
    Page<User> userPage = userRepository.findAll(Example.of(param.getParam()), PageHelper.page(param));
    return new PageResult<>(userPage);
  }

  @Override
  @Transactional
  public boolean delete(Long id) {
    User dbUser = this.getOneById(id);
    Assert.state(dbUser != null, Constants.DELETE_NOT_EXISTS_ERROR);
    dbUser.setDeleted(true);
    userRepository.saveAndFlush(dbUser);
    return true;
  }
}

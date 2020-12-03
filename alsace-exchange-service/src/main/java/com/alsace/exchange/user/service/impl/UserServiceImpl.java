package com.alsace.exchange.user.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.PageHelper;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.user.domain.User;
import com.alsace.exchange.user.repositories.UserRepository;
import com.alsace.exchange.user.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Resource
  private UserRepository userRepository;


  @Override
  public User findOne(User user) {
    Example<User> example = Example.of(user);
    return userRepository.findOne(example).orElse(null);
  }

  @Override
  public User getOneById(Long id) {
    return userRepository.getOne(id);
  }

  @Override
  @AutoFill(AutoFillType.CREATE)
  @Transactional(rollbackFor = Exception.class)
  public User save(User param) {
    param.setLocked(false);
    return userRepository.saveAndFlush(param);
  }

  @Override
  @AutoFill(AutoFillType.CREATE)
  @Transactional(rollbackFor = Exception.class)
  public List<User> save(List<User> param) {
    param.forEach(user -> user.setLocked(false));
    return userRepository.saveAll(param);
  }

  @Override
  public PageResult<User> findPage(PageParam<User> param) {
    Page<User> userPage = userRepository.findAll(Example.of(param.getParam()), PageHelper.page(param));
    return new PageResult<>(userPage);
  }
}

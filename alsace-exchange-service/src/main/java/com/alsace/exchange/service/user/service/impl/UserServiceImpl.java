package com.alsace.exchange.service.user.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.Constants;
import com.alsace.exchange.common.base.PageHelper;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.service.user.domain.User;
import com.alsace.exchange.service.user.repositories.UserRepository;
import com.alsace.exchange.service.user.repositories.UserSpecs;
import com.alsace.exchange.service.user.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    User userParam = new User();
    userParam.setLoginAccount(param.getLoginAccount()).setDeleted(false);
    long userCount = userRepository.count(Example.of(userParam));
    if (userCount > 0) {
      throw new AlsaceException(String.format("用户名%s已经存在！", param.getLoginAccount()));
    }
    param.setLocked(false);
    return userRepository.saveAndFlush(param);
  }

  @Override
  public List<User> save(List<User> param) {
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION);
  }

  @Override
  public PageResult<User> findPage(PageParam<User> param) {
    Page<User> userPage = userRepository.findAll(Example.of(param.getParam()), PageHelper.page(param));
    return new PageResult<>(userPage);
  }
}

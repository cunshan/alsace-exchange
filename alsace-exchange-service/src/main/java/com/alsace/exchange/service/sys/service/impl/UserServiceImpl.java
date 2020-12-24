package com.alsace.exchange.service.sys.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.domain.UserRole;
import com.alsace.exchange.service.sys.repositories.UserRepository;
import com.alsace.exchange.service.sys.repositories.UserRoleRepository;
import com.alsace.exchange.service.sys.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl extends AbstractBaseServiceImpl<User> implements UserService {

  @Resource
  private UserRepository userRepository;
  @Resource
  private UserRoleRepository userRoleRepository;

  @PostConstruct
  protected void init() {
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

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean addUserRoles(String loginAccount, List<String> roleList) {
    Assert.hasLength(loginAccount,"登录账号为空!");
    Assert.notEmpty(roleList,"角色不能为空！");
    //验证用户是否存在
    User up = new User();
    up.setLoginAccount(loginAccount).setDeleted(false);
    User user = this.findOne(up);
    Assert.state(user != null, String.format("用户【%s】不存在！", loginAccount));
    //删除用户对应的角色
    userRoleRepository.deleteAllByLoginAccount(loginAccount);
    //为用户增加新角色
    List<UserRole> userRoleList = new ArrayList<>(roleList.size());
    new HashSet<>(roleList).forEach(role->{
      UserRole userRole = new UserRole(loginAccount,role);
      userRole.setDeleted(false)
          .setId(IdUtils.id())
          .setCreatedDate(new Date())
          .setCreatedBy(getLoginAccount());
      userRoleList.add(userRole);
    });
    userRoleRepository.saveAll(userRoleList);
    return true;
  }
}

package com.alsace.exchange.service.sys.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.base.AlsacePageHelper;
import com.alsace.exchange.common.base.LoginInfoProvider;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.domain.UserData;
import com.alsace.exchange.service.sys.domain.UserImport;
import com.alsace.exchange.service.sys.domain.UserRole;
import com.alsace.exchange.service.sys.enums.OrderByEnum;
import com.alsace.exchange.service.sys.excel.UserImportVerifyService;
import com.alsace.exchange.service.sys.repositories.UserRepository;
import com.alsace.exchange.service.sys.repositories.UserRoleRepository;
import com.alsace.exchange.service.sys.service.UserDataService;
import com.alsace.exchange.service.sys.service.UserService;
import com.alsace.exchange.service.sys.specs.UserSpecs;
import com.alsace.exchange.service.utils.JpaHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl extends AbstractBaseServiceImpl<User> implements UserService {

  @Resource
  private UserRepository userRepository;
  @Resource
  private UserRoleRepository userRoleRepository;
  @Resource
  private UserImportVerifyService userImportVerifyService;
  @Resource
  private LoginInfoProvider loginInfoProvider;
  @Resource
  private UserDataService userDataService;

  @Override
  protected JpaRepository<User, Long> getJpaRepository() {
    return this.userRepository;
  }

  @Override
  protected JpaSpecificationExecutor<User> getJpaSpecificationExecutor() {
    return this.userRepository;
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
    param.setPassword(DigestUtils.md5Hex(param.getPassword().trim() + param.getLoginAccount()));
    return userRepository.saveAndFlush(param);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean addUserRoles(String loginAccount, List<String> roleList) {
    Assert.hasLength(loginAccount, "登录账号为空!");
    Assert.notEmpty(roleList, "角色不能为空！");
    checkUser(loginAccount);
    //删除用户对应的角色
    userRoleRepository.deleteAllByLoginAccount(loginAccount);
    //为用户增加新角色
    List<UserRole> userRoleList = new ArrayList<>(roleList.size());
    new HashSet<>(roleList).forEach(role -> {
      UserRole userRole = new UserRole(loginAccount, role);
      setCreateInfo(userRole);
      userRoleList.add(userRole);
    });
    userRoleRepository.saveAll(userRoleList);
    return true;
  }

  /**
   * 校验用户是否存在
   */
  private User checkUser(String loginAccount) {
    //验证用户是否存在
    User up = new User();
    up.setLoginAccount(loginAccount).setDeleted(false);
    User user = this.findOne(up);
    Assert.state(user != null, String.format("用户【%s】不存在！", loginAccount));
    return user;
  }

  @Override
  public List<User> importUser(List<Object> param) {
    InputStream is = new ByteArrayInputStream((byte[]) param.get(0));
    ImportParams params = new ImportParams();
    params.setTitleRows(1);
//    params.setStartRows(1);
    // 开启Excel校验
    params.setNeedVerfiy(true);
    params.setVerifyHandler(userImportVerifyService);
    try {
      ExcelImportResult<UserImport> importResult = ExcelImportUtil.importExcelMore(is, UserImport.class, params);
      if (importResult.isVerfiyFail()) {
        StringBuffer sb = new StringBuffer();
        for (UserImport entity : importResult.getFailList()) {
          sb.append(String.format("第%s行的错误是:%s", entity.getRowNum(), entity.getErrorMsg()));
        }
        throw new AlsaceException(sb.toString());
      }
      List<User> users = new ArrayList<>();
      importResult.getList().forEach(userImport -> {
        User user = new User();
        BeanUtils.copyProperties(userImport, user);
        user.setId(IdUtils.id());
        user.setLocked(false);
        user.setDeleted(false);
        user.setWorking(true);
        user.setCreatedBy(loginInfoProvider.loginAccount());
        user.setCreatedDate(new Date());
        user.setPassword(DigestUtils.md5Hex("123456"));
        users.add(user);
      });
      return userRepository.saveAll(users);
    } catch (Exception e) {
      throw new AlsaceException("导入用户数据异常！" + e.getMessage());
    }
  }

  @Override
  public List<User> findAllByLoginAccounts(List<String> accountList) {
    return this.userRepository.findAll(UserSpecs.loginAccountIn(accountList).and(UserSpecs.deleted(false)));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  @AutoFill(AutoFillType.CREATE)
  public boolean addUserData(String loginAccount, List<UserData> dataList) {
    Assert.hasLength(loginAccount, "登录账号为空！");
    Assert.notEmpty(dataList, "数据权限列表为空！");
    checkUser(loginAccount);
    //删除用户对应数据权限
    userDataService.deleteByLoginAccount(loginAccount);
    //为用户增加新的数据权限
    dataList.forEach(data -> data.setLoginAccount(loginAccount));
    userDataService.saveBatch(dataList);
    return true;
  }

  @Override
  public PageResult<User> findPage(PageParam<User> param) {
    if (param.getParam() == null) {
      throw new AlsaceException("参数对象为空！");
    }
    param.getParam().setDeleted(false);
    User user = param.getParam();
    //Specification<User> specification = UserSpecs.build(user);
    Set<String> likeSet = new HashSet<>();
    likeSet.add("userName");
    likeSet.add("nickName");
    LinkedHashMap<String, OrderByEnum> orderMap = new LinkedHashMap<>();
    orderMap.put("createdDate",OrderByEnum.DESC);
    Specification<User> specification = JpaHelper.buildConditions(user,likeSet,orderMap);
    return new PageResult<>(getJpaSpecificationExecutor().findAll(specification, AlsacePageHelper.page(param)));
  }
}

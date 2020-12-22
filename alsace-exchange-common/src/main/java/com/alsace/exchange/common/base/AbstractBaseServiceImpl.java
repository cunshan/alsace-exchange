package com.alsace.exchange.common.base;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.utils.AlsaceBeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

public class AbstractBaseServiceImpl<T extends BaseEntity> implements BaseService<T,Long> {

  protected JpaRepository<T,Long> jpaRepository;

  protected JpaSpecificationExecutor<T> specificationExecutor;
  @Resource
  protected LoginInfoProvider loginInfoProvider;

  /**
   * 获取当前登录人账号
   */
  protected String getLoginAccount(){
    return loginInfoProvider.loginAccount();
  }
  /**
   * 获取当前登录人姓名
   */
  protected String getUserName(){
    return loginInfoProvider.userName();
  }

  @Override
  public T findOne(T t) {
    Example<T> example = Example.of(t);
    return jpaRepository.findOne(example).orElse(null);
  }

  @Override
  public T getOneById(Long id) {
    Assert.notNull(id, Constants.ID_NOT_NULL_ERROR);
    return jpaRepository.findById(id).orElse(null);
  }

  @Override
  @AutoFill(AutoFillType.CREATE)
  @Transactional(rollbackFor = Exception.class)
  public T save(T param) {
    return jpaRepository.saveAndFlush(param);
  }

  @Override
  public List<T> saveBatch(List<T> param) {
    return jpaRepository.saveAll(param);
  }

  @Override
  @AutoFill(AutoFillType.UPDATE)
  @Transactional(rollbackFor = Exception.class)
  public T update(T param) {
    T dbUser = this.getOneById(param.getId());
    Assert.state(dbUser != null, Constants.UPDATE_NOT_EXISTS_ERROR);
    AlsaceBeanUtils.copyNotNullProperties(param,dbUser);
    return jpaRepository.saveAndFlush(dbUser);
  }

  @Override
  public PageResult<T> findPage(PageParam<T> param) {
    Page<T> userPage = jpaRepository.findAll(Example.of(param.getParam()), PageHelper.page(param));
    return new PageResult<>(userPage);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean delete(Long id) {
    T dbUser = this.getOneById(id);
    Assert.state(dbUser != null, Constants.DELETE_NOT_EXISTS_ERROR);
    dbUser.setDeleted(true);
    jpaRepository.saveAndFlush(dbUser);
    return true;
  }
}

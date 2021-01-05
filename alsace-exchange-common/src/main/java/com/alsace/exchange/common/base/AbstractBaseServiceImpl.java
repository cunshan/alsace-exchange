package com.alsace.exchange.common.base;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.AlsaceBeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBaseServiceImpl<T extends BaseEntity> implements BaseService<T, Long> {

  @Resource
  protected LoginInfoProvider loginInfoProvider;

  protected abstract JpaRepository<T, Long> getJpaRepository();

  protected abstract JpaSpecificationExecutor<T> getJpaSpecificationExecutor();

  /**
   * 获取当前登录人账号
   */
  protected String getLoginAccount() {
    return loginInfoProvider.loginAccount();
  }

  /**
   * 获取当前登录人姓名
   */
  protected String getUserName() {
    return loginInfoProvider.userName();
  }

  @Override
  public T findOne(T t) {
    Example<T> example = Example.of(t);
    return getJpaRepository().findOne(example).orElse(null);
  }

  @Override
  public T getOneById(Long id) {
    Assert.notNull(id, Constants.ID_NOT_NULL_ERROR);
    return getJpaRepository().findById(id).orElse(null);
  }

  @Override
  @AutoFill(AutoFillType.CREATE)
  @Transactional(rollbackFor = Exception.class)
  public T save(T param) {
    return getJpaRepository().saveAndFlush(param);
  }

  @Override
  @AutoFill(AutoFillType.CREATE)
  @Transactional(rollbackFor = Exception.class)
  public List<T> saveBatch(List<T> param) {
    return getJpaRepository().saveAll(param);
  }

  @Override
  @AutoFill(AutoFillType.UPDATE)
  @Transactional(rollbackFor = Exception.class)
  public T update(T param) {
    Assert.notNull(param.getId(), Constants.ID_NOT_NULL_ERROR);
    T dbUser = this.getOneById(param.getId());
    Assert.state(dbUser != null, Constants.UPDATE_NOT_EXISTS_ERROR);
    AlsaceBeanUtils.copyNotNullProperties(param, dbUser);
    return getJpaRepository().saveAndFlush(dbUser);
  }

  @Override
  public PageResult<T> findPage(PageParam<T> param) {
    if(param.getParam()==null){
      throw new AlsaceException("参数对象为空！");
    }
    param.getParam().setDeleted(false);
    Page<T> userPage = getJpaRepository().findAll(Example.of(param.getParam()), AlsacePageHelper.page(param));
    return new PageResult<>(userPage);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean delete(List<Long> idList) {
    Assert.notEmpty(idList,"ID列表为空！");
    List<T> domainList = new ArrayList<>(idList.size());
    idList.forEach(id->{
      T dbUser = this.getOneById(id);
      Assert.state(dbUser != null, Constants.DELETE_NOT_EXISTS_ERROR);
      dbUser.setDeleted(true);
      domainList.add(dbUser);
    });
    getJpaRepository().saveAll(domainList);
    return true;
  }

  @Override
  public List<T> findAll(T param) {
    if (param == null) {
      return getJpaRepository().findAll();
    }
    return getJpaRepository().findAll(Example.of(param));
  }
}

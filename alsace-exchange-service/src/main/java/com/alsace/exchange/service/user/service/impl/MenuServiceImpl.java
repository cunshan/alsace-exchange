package com.alsace.exchange.service.user.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.base.PageHelper;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.service.user.domain.Menu;
import com.alsace.exchange.service.user.repositories.MenuRepository;
import com.alsace.exchange.service.user.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class MenuServiceImpl extends AbstractBaseServiceImpl<Menu, Long> implements MenuService {

  @Resource
  private MenuRepository menuRepository;

  @Override
  public Menu findOne(Menu menu) {
    return menuRepository.findOne(Example.of(menu)).orElse(null);
  }

  @Override
  public Menu getOneById(Long id) {
    Assert.notNull(id, Constants.ID_NOT_NULL_ERROR);
    return menuRepository.findById(id).orElse(null);
  }

  @Override
  @AutoFill(AutoFillType.CREATE)
  @Transactional
  public Menu save(Menu param) {
    return menuRepository.saveAndFlush(param);
  }

  @Override
  @AutoFill(AutoFillType.UPDATE)
  @Transactional
  public Menu update(Menu param) {
    Menu dbMenu = this.getOneById(param.getId());
    Assert.state(dbMenu != null, Constants.UPDATE_NOT_EXISTS_ERROR);
    BeanUtils.copyProperties(param, dbMenu);
    return menuRepository.saveAndFlush(dbMenu);
  }

  @Override
  public PageResult<Menu> findPage(PageParam<Menu> param) {
    Page<Menu> menuPage = menuRepository.findAll(Example.of(param.getParam()), PageHelper.page(param));
    return new PageResult<>(menuPage);
  }

  @Override
  @Transactional
  public boolean delete(Long id) {
    Menu dbMenu = this.getOneById(id);
    Assert.state(dbMenu != null, Constants.DELETE_NOT_EXISTS_ERROR);
    dbMenu.setDeleted(true);
    menuRepository.saveAndFlush(dbMenu);
    return true;
  }
}

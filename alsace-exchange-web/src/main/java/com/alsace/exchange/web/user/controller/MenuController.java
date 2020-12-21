package com.alsace.exchange.web.user.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.user.domain.Menu;
import com.alsace.exchange.service.user.service.MenuService;
import com.alsace.exchange.service.user.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "菜单",value = "menu")
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {
  
  @Resource
  private MenuService menuService;

  @ApiOperation("菜单保存")
  @PostMapping("/save")
  public AlsaceResult<Menu> save(@RequestBody Menu param) {
    return success(menuService.save(param));
  }

  @ApiOperation("菜单分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<Menu>> page(@RequestBody PageParam<Menu> param) {
    PageResult<Menu> page = menuService.findPage(param);
    return success(page);
  }

  @ApiOperation("菜单更新")
  @PostMapping("/update")
  public AlsaceResult<Menu> update(@RequestBody Menu param) {
    Menu domain = menuService.update(param);
    return success(domain);
  }

  @ApiOperation("菜单删除")
  @PostMapping("/delete/{id}")
  public AlsaceResult<String> update(@PathVariable Long id) {
    menuService.delete(id);
    return success("删除成功",null);
  }

}

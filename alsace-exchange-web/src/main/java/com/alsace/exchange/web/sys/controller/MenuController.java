package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.TreeVo;
import com.alsace.exchange.service.sys.domain.Menu;
import com.alsace.exchange.service.sys.service.MenuService;
import com.alsace.exchange.web.sys.biz.MenuHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "菜单",value = "menu")
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {
  
  @Resource
  private MenuService menuService;
  @Resource
  private MenuHandler menuHandler;

  @ApiOperation("菜单保存")
  @PostMapping("/save")
  public AlsaceResult<Menu> save(@RequestBody Menu param) {
    return success(menuService.save(param));
  }

  @ApiOperation("菜单树形结构查询")
  @PostMapping("/tree/{parentId}")
  public AlsaceResult<List<TreeVo<Menu>>> tree(@PathVariable Long parentId) {
    return success(menuHandler.tree(parentId));
  }

  @ApiOperation("所有菜单树形结构查询")
  @PostMapping("/tree")
  public AlsaceResult<List<TreeVo<Menu>>> treeAll() {
    return success(menuHandler.tree(null));
  }

  @ApiOperation("菜单更新")
  @PostMapping("/update")
  public AlsaceResult<Menu> update(@RequestBody Menu param) {
    Menu domain = menuService.update(param);
    return success(domain);
  }

  @ApiOperation("菜单删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    menuService.delete(idList);
    return success("删除成功",null);
  }

  @ApiOperation("根据角色编码获取菜单")
  @PostMapping("/query-by-role/{roleCode}")
  public AlsaceResult<List<TreeVo<Menu>>> queryByRole(@PathVariable String roleCode) {
    return success(menuHandler.queryByRole(roleCode));
  }

}

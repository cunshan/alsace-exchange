package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.sys.domain.Role;
import com.alsace.exchange.service.sys.service.RoleService;
import com.alsace.exchange.web.sys.vo.RoleMenuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "角色",value = "role")
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
  
  @Resource
  private RoleService roleService;

  @ApiOperation("角色保存")
  @PostMapping("/save")
  public AlsaceResult<Role> save(@RequestBody Role param) {
    return success(roleService.save(param));
  }

  @ApiOperation("角色分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<Role>> page(@RequestBody PageParam<Role> param) {
    PageResult<Role> page = roleService.findPage(param);
    return success(page);
  }

  @ApiOperation("角色更新")
  @PostMapping("/update")
  public AlsaceResult<Role> update(@RequestBody Role param) {
    Role domain = roleService.update(param);
    return success(domain);
  }

  @ApiOperation("角色删除")
  @PostMapping("/delete/{id}")
  public AlsaceResult<String> delete(@PathVariable Long id) {
    roleService.delete(id);
    return success("删除成功",null);
  }

  @ApiOperation("角色添加菜单")
  @PostMapping("/add-menus")
  public AlsaceResult<String> addMenus(@RequestBody RoleMenuVo param){
    roleService.addMenus(param.getRoleCode(),param.getMenuIdList());
    return success("更新成功！",null);
  }

}

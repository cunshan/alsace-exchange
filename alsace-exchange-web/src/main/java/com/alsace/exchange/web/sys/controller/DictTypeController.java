package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.sys.domain.DictType;
import com.alsace.exchange.service.sys.domain.DictValue;
import com.alsace.exchange.service.sys.service.DictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "基础数据",value = "dictType")
@RestController
@RequestMapping("/dict/type")
public class DictTypeController extends BaseController {
  
  @Resource
  private DictTypeService dictTypeService;


  @ApiOperation("数据字典保存")
  @PostMapping("/save")
  public AlsaceResult<DictType> save(@RequestBody DictType param) {
    return success(dictTypeService.save(param));
  }

  @ApiOperation("数据字典分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<DictType>> page(@RequestBody PageParam<DictType> param) {
    PageResult<DictType> page = dictTypeService.findPage(param);
    return success(page);
  }
  
  @ApiOperation("数据字典更新")
  @PostMapping("/update")
  public AlsaceResult<DictType> update(@RequestBody DictType param) {
    DictType domain = dictTypeService.update(param);
    return success(domain);
  }

  @ApiOperation("数据字典删除")
  @PostMapping("/delete/{id}")
  public AlsaceResult<String> delete(@PathVariable Long id) {
    dictTypeService.delete(id);
    return success("删除成功",null);
  }



}

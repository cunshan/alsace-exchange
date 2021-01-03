package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.sys.domain.DictValue;
import com.alsace.exchange.service.sys.service.DictValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "基础数据",value = "dictValue")
@RestController
@RequestMapping("/dict/value")
public class DictValueController extends BaseController {
  
  @Resource
  private DictValueService dictValueService;


  @ApiOperation("数据字典值保存")
  @PostMapping("/save")
  public AlsaceResult<DictValue> save(@RequestBody DictValue param) {
    return success(dictValueService.save(param));
  }

  @ApiOperation("数据字典值分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<DictValue>> page(@RequestBody PageParam<DictValue> param) {
    PageResult<DictValue> page = dictValueService.findPage(param);
    return success(page);
  }
  
  @ApiOperation("数据字典值更新")
  @PostMapping("/update")
  public AlsaceResult<DictValue> update(@RequestBody DictValue param) {
    DictValue domain = dictValueService.update(param);
    return success(domain);
  }

  @ApiOperation("数据字典值删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    dictValueService.delete(idList);
    return success("删除成功",null);
  }


  @ApiOperation("按照数据字典编码查询数据字典值")
  @PostMapping("/find-by-type-code/{typeCode}")
  public AlsaceResult<List<DictValue>> findByType(@PathVariable String typeCode){
    Assert.hasLength(typeCode,"数据字典编码为空！");
    DictValue param = new DictValue();
    param.setTypeCode(typeCode)
        .setDeleted(false);
    return success(dictValueService.findAll(param));
  }

}

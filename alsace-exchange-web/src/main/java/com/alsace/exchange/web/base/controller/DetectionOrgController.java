package com.alsace.exchange.web.base.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.base.domain.DetectionOrg;
import com.alsace.exchange.service.base.service.DetectionOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "检测机构",value = "detectionOrg")
@RestController
@RequestMapping("/detection")
public class DetectionOrgController extends BaseController {

  @Resource
  private DetectionOrgService detectionOrgService;


  @ApiOperation("检测机构保存")
  @PostMapping("/save")
  public AlsaceResult<DetectionOrg> save(@RequestBody DetectionOrg param) {
    return success(detectionOrgService.save(param));
  }

  @ApiOperation("检测机构分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<DetectionOrg>> page(@RequestBody PageParam<DetectionOrg> param) {
    PageResult<DetectionOrg> page = detectionOrgService.findPage(param);
    return success(page);
  }

  @ApiOperation("检测机构更新")
  @PostMapping("/update")
  public AlsaceResult<DetectionOrg> update(@RequestBody DetectionOrg param) {
    DetectionOrg domain = detectionOrgService.update(param);
    return success(domain);
  }

  @ApiOperation("检测机构删除")
  @PostMapping("/delete/{id}")
  public AlsaceResult<String> delete(@PathVariable Long id) {
    detectionOrgService.delete(id);
    return success("删除成功",null);
  }

}

package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskForm;
import com.alsace.exchange.service.detection.service.EnvironmentTaskFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "环境监测任务表单", value = "environmentTaskForm")
@RestController
@RequestMapping("/env-task/form")
public class EnvironmentTaskFormController extends BaseController {

  @Resource
  private EnvironmentTaskFormService environmentTaskFormService;

  @ApiOperation("环境监测任务表单分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<EnvironmentTaskForm>> page(@RequestBody PageParam<EnvironmentTaskForm> param) {
    PageResult<EnvironmentTaskForm> page = environmentTaskFormService.findPage(param);
    return success(page);
  }

  @ApiOperation("环境监测任务表单更新")
  @PostMapping("/update")
  public AlsaceResult<EnvironmentTaskForm> update(@RequestBody EnvironmentTaskForm param) {
    EnvironmentTaskForm domain = environmentTaskFormService.update(param);
    return success(domain);
  }

}

package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.LoginInfoProvider;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import com.alsace.exchange.service.detection.service.PersonTaskFormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "人员检测任务表单", value = "personTaskForm")
@RestController
@RequestMapping("/person-task/form")
public class PersonTaskFormController extends BaseController {

  @Resource
  private PersonTaskFormService personTaskFormService;

  @ApiOperation("人员检测任务表单分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<PersonTaskForm>> page(@RequestBody PageParam<PersonTaskForm> param) {
    PageResult<PersonTaskForm> page = personTaskFormService.findPage(param);
    return success(page);
  }

  @ApiOperation("人员检测任务表单更新")
  @PostMapping("/update")
  public AlsaceResult<PersonTaskForm> update(@RequestBody PersonTaskForm param) {
    PersonTaskForm domain = personTaskFormService.update(param);
    return success(domain);
  }

}

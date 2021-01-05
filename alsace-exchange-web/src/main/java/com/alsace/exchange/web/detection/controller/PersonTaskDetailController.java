package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "人员检测任务明细",value = "personTaskDetail")
@RestController
@RequestMapping("/person-task/detail")
public class PersonTaskDetailController extends BaseController {

  @Resource
  private PersonTaskDetailService personTaskDetailService;

  @ApiOperation("人员检测任务明细分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<PersonTaskDetail>> page(@RequestBody PageParam<PersonTaskDetail> param) {
    PageResult<PersonTaskDetail> page = personTaskDetailService.findPage(param);
    return success(page);
  }

  @ApiOperation("人员检测任务明细更新")
  @PostMapping("/update")
  public AlsaceResult<PersonTaskDetail> update(@RequestBody PersonTaskDetail param) {
    PersonTaskDetail domain = personTaskDetailService.update(param);
    return success(domain);
  }

  @ApiOperation("人员检测任务明细删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    personTaskDetailService.delete(idList);
    return success("删除成功",null);
  }

}

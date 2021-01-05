package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.service.detection.domain.PersonTaskApp;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import com.alsace.exchange.service.detection.service.PersonTaskFormService;
import com.alsace.exchange.service.detection.service.PersonTaskService;
import com.alsace.exchange.web.detection.vo.PersonTaskDetailPageVo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app")
@ApiOperation(value = "APP对应接口", tags = "APP对应接口")
public class AppController extends BaseController {

  @Resource
  private PersonTaskService personTaskService;
  @Resource
  private PersonTaskDetailService personTaskDetailService;
  @Resource
  private PersonTaskFormService personTaskFormService;

  @ApiOperation(value = "APP上人员检测任务列表查询")
  @PostMapping("/person/page")
  public AlsaceResult<PageResult<PersonTaskApp>> personTaskPage(PageParam pageParam) {
    return success(personTaskService.findPersonTaskApp(pageParam));
  }

  @ApiOperation(value = "开始人员检测任务")
  @PostMapping("/person/start/{taskCode}")
  public AlsaceResult<PersonTaskForm> startTask(@PathVariable String taskCode) {
    return success(personTaskService.startTask(taskCode));
  }

  @ApiOperation(value = "获取单条人员检测任务明细")
  @PostMapping("/person/detail/get-one")
  public AlsaceResult<PersonTaskDetail> detail(@RequestBody PersonTaskDetail param) {
    param.setDeleted(false);
    PersonTaskDetail detail = personTaskDetailService.findOne(param);
    if (detail == null) {
      throw new AlsaceException("被检测人员信息不存在！");
    }
    return success(detail);
  }

  @ApiOperation(value = "提交检测明细")
  @PostMapping("/person/detail/submit")
  public AlsaceResult<String> submit(@RequestBody PersonTaskDetail param) {
    personTaskService.submitDetail(param);
    return success("提交成功", null);
  }


  @ApiOperation(value = "APP人员检测任务明细分页查询")
  @PostMapping("/person/form/page")
  public AlsaceResult<PersonTaskDetailPageVo> personPage(@RequestBody PageParam<PersonTaskDetailPageVo> param) {
    Assert.notNull(param.getParam(), "查询参数为空！");
    PersonTaskDetailPageVo paramVo = param.getParam();
    Assert.hasLength(paramVo.getTaskCode(), "任务编码不能为空！");
    PageParam<PersonTaskDetail> detailPage = new PageParam<>();
    detailPage.setPageNum(param.getPageNum()).setPageSize(param.getPageSize());
    PersonTaskDetail queryParam = new PersonTaskDetail();
    queryParam.setTaskCode(paramVo.getTaskCode());
    queryParam.setFormCode(paramVo.getFormCode());
    queryParam.setDeleted(false);
    queryParam.setCreatedBy(super.loginInfoProvider.loginAccount());
    detailPage.setParam(queryParam);
    PageResult<PersonTaskDetail> page = personTaskDetailService.findPage(detailPage);
    PersonTaskDetailPageVo resVo = new PersonTaskDetailPageVo();
    resVo.setDetailList(page.getRecords()).setTaskCode(paramVo.getTaskCode()).setFormCode(paramVo.getFormCode());
    return success(resVo);
  }

  @ApiOperation(value = "人员检测任务表单提交")
  @PostMapping("/person/form/submit")
  public AlsaceResult<PersonTaskForm> submit(@RequestBody PersonTaskForm param) {
    personTaskFormService.submitForm(param);
    return success("提交成功", null);
  }

}

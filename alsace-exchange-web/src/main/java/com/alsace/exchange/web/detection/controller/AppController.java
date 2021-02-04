package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.service.detection.domain.EnvironmentTask;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskForm;
import com.alsace.exchange.service.detection.domain.PersonTaskApp;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import com.alsace.exchange.service.detection.service.EnvironmentTaskDetailService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskFormService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskService;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import com.alsace.exchange.service.detection.service.PersonTaskFormService;
import com.alsace.exchange.service.detection.service.PersonTaskService;
import com.alsace.exchange.web.config.log.annontation.Log;
import com.alsace.exchange.web.detection.vo.EnvironmentTaskDetailPageVo;
import com.alsace.exchange.web.detection.vo.PersonTaskDetailPageVo;
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

@RestController
@RequestMapping("/app")
@Api(value = "APP对应接口", tags = "APP对应接口")
public class AppController extends BaseController {

  @Resource
  private PersonTaskService personTaskService;
  @Resource
  private PersonTaskDetailService personTaskDetailService;
  @Resource
  private PersonTaskFormService personTaskFormService;
  @Resource
  private EnvironmentTaskService environmentTaskService;
  @Resource
  private EnvironmentTaskDetailService environmentTaskDetailService;
  @Resource
  private EnvironmentTaskFormService environmentTaskFormService;

  @ApiOperation(value = "人员检测任务列表查询")
  @PostMapping("/person/page")
  @Log(value = "人员检测任务列表查询",moduleName = "APP")
  public AlsaceResult<PageResult<PersonTaskApp>> personTaskPage(@RequestBody PageParam<PersonTaskApp> param) {
    return success(personTaskService.findPersonTaskApp(param));
  }

  @ApiOperation(value = "开始人员检测任务")
  @PostMapping("/person/start/{taskCode}")
  @Log(value = "开始人员检测任务",moduleName = "APP")
  public AlsaceResult<PersonTaskForm> startTask(@PathVariable String taskCode) {
    return success(personTaskService.startTask(taskCode));
  }

  @ApiOperation(value = "获取单条人员检测任务明细")
  @PostMapping("/person/detail/get-one")
  @Log(value = "获取单条人员检测任务明细",moduleName = "APP")
  public AlsaceResult<PersonTaskDetail> detail(@RequestBody PersonTaskDetail param) {
    param.setDeleted(false);
    PersonTaskDetail detail = personTaskDetailService.findOne(param);
    if (detail == null) {
      throw new AlsaceException("被检测人员信息不存在！");
    }
    return success(detail);
  }

  @ApiOperation(value = "提交人员检测明细")
  @PostMapping("/person/detail/submit")
  @Log(value = "提交人员检测明细",moduleName = "APP")
  public AlsaceResult<String> submitDetail(@RequestBody PersonTaskDetail param) {
    personTaskService.submitDetail(param);
    return success("提交成功", null);
  }


  @ApiOperation(value = "人员检测任务明细分页查询")
  @PostMapping("/person/form/page")
  @Log(value = "人员检测任务明细分页查询",moduleName = "APP")
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
    detailPage.setParam(queryParam);
    PageResult<PersonTaskDetail> page = personTaskDetailService.findFromPage(detailPage);
    PersonTaskDetailPageVo resVo = new PersonTaskDetailPageVo();
    resVo.setDetailList(page.getRecords()).setTaskCode(paramVo.getTaskCode()).setFormCode(paramVo.getFormCode());
    return success(resVo);
  }

  @ApiOperation(value = "人员检测任务表单提交")
  @PostMapping("/person/form/submit")
  @Log(value = "人员检测任务表单提交",moduleName = "APP")
  public AlsaceResult<PersonTaskForm> submitForm(@RequestBody List<String> formCodeList) {
    personTaskFormService.submitForm(formCodeList);
    return success("提交成功", null);
  }

  //============ 环境检测 =============

  @ApiOperation(value = "环境检测任务列表查询")
  @PostMapping("/env/page")
  @Log(value = "环境检测任务列表查询",moduleName = "APP")
  public AlsaceResult<PageResult<EnvironmentTask>> envTaskPage(@RequestBody PageParam<EnvironmentTask> param) {
    return success(environmentTaskService.findEnvironmentTaskApp(param));
  }

  @ApiOperation(value = "开始环境检测任务")
  @PostMapping("/env/start/{taskCode}")
  @Log(value = "开始环境检测任务",moduleName = "APP")
  public AlsaceResult<EnvironmentTaskForm> envStartTask(@PathVariable String taskCode) {
    return success(environmentTaskService.startTask(taskCode));
  }

  @ApiOperation(value = "获取单条环境检测任务明细")
  @PostMapping("/env/detail/get-one")
  @Log(value = "获取单条环境检测任务明细",moduleName = "APP")
  public AlsaceResult<EnvironmentTaskDetail> envDetail(@RequestBody EnvironmentTaskDetail param) {
    param.setDeleted(false);
    EnvironmentTaskDetail detail = environmentTaskDetailService.findOne(param);
    if (detail == null) {
      throw new AlsaceException("被检测环境信息不存在！");
    }
    return success(detail);
  }

  @ApiOperation(value = "提交环境检测明细")
  @PostMapping("/env/detail/submit")
  @Log(value = "提交环境检测明细",moduleName = "APP")
  public AlsaceResult<String> envSubmitDetail(@RequestBody EnvironmentTaskDetail param) {
    environmentTaskService.submitDetail(param);
    return success("提交成功", null);
  }


  @ApiOperation(value = "APP人员检测任务明细分页查询")
  @PostMapping("/env/form/page")
  @Log(value = "APP人员检测任务明细分页查询",moduleName = "APP")
  public AlsaceResult<EnvironmentTaskDetailPageVo> envPage(@RequestBody PageParam<EnvironmentTaskDetailPageVo> param) {
    Assert.notNull(param.getParam(), "查询参数为空！");
    EnvironmentTaskDetailPageVo paramVo = param.getParam();
    Assert.hasLength(paramVo.getTaskCode(), "任务编码不能为空！");
    PageParam<EnvironmentTaskDetail> detailPage = new PageParam<>();
    detailPage.setPageNum(param.getPageNum()).setPageSize(param.getPageSize());
    EnvironmentTaskDetail queryParam = new EnvironmentTaskDetail();
    queryParam.setTaskCode(paramVo.getTaskCode());
    queryParam.setFormCode(paramVo.getFormCode());
    queryParam.setDeleted(false);
    queryParam.setCreatedBy(super.loginInfoProvider.loginAccount());
    detailPage.setParam(queryParam);
    PageResult<EnvironmentTaskDetail> page = environmentTaskDetailService.findPage(detailPage);
    EnvironmentTaskDetailPageVo resVo = new EnvironmentTaskDetailPageVo();
    resVo.setDetailList(page.getRecords()).setTaskCode(paramVo.getTaskCode()).setFormCode(paramVo.getFormCode());
    return success(resVo);
  }

  @ApiOperation(value = "人员检测任务表单提交")
  @PostMapping("/env/form/submit")
  @Log(value = "人员检测任务表单提交",moduleName = "APP")
  public AlsaceResult<EnvironmentTaskForm> envSubmitForm(@RequestBody List<String> formCodeList) {
    environmentTaskFormService.submitForm(formCodeList);
    return success("提交成功", null);
  }

}

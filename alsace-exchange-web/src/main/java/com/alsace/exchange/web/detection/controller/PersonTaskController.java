package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import com.alsace.exchange.service.detection.service.PersonTaskLocationService;
import com.alsace.exchange.service.detection.service.PersonTaskOperatorService;
import com.alsace.exchange.service.detection.service.PersonTaskOrgService;
import com.alsace.exchange.service.detection.service.PersonTaskService;
import com.alsace.exchange.web.detection.vo.PersonTaskDetailVo;
import com.alsace.exchange.web.detection.vo.PersonTaskOperatorVo;
import com.alsace.exchange.web.detection.vo.PersonTaskVo;
import com.alsace.exchange.web.detection.vo.TaskResultBatchVo;
import com.alsace.exchange.web.detection.vo.TaskResultSingleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "人员检测任务", value = "personTask")
@RestController
@RequestMapping("/person-task/task")
public class PersonTaskController extends BaseController {

  @Resource
  private PersonTaskService personTaskService;
  @Resource
  private PersonTaskOrgService personTaskOrgService;
  @Resource
  private PersonTaskLocationService personTaskLocationService;
  @Resource
  private PersonTaskOperatorService personTaskOperatorService;
  @Resource
  private PersonTaskDetailService personTaskDetailService;


  @ApiOperation("人员检测任务保存")
  @PostMapping("/save")
  public AlsaceResult<PersonTask> save(@RequestBody PersonTaskVo param) {
    return success(personTaskService.save(param.getTask(), param.getOrgList(), param.getLocationList()));
  }

  @ApiOperation("人员检测任务分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<PersonTask>> page(@RequestBody PageParam<PersonTask> param) {
    PageResult<PersonTask> page = personTaskService.findPage(param);
    return success(page);
  }

  @ApiOperation("人员检测任务更新")
  @PostMapping("/update")
  public AlsaceResult<PersonTask> update(@RequestBody PersonTaskVo param) {
    PersonTask domain = personTaskService.update(param.getTask(), param.getOrgList(), param.getLocationList());
    return success(domain);
  }

  @ApiOperation("人员检测任务删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    personTaskService.delete(idList);
    return success("删除成功", null);
  }

  @ApiOperation("人员检测任务添加检测人员")
  @PostMapping("/add-operators")
  public AlsaceResult<String> addOperators(@RequestBody PersonTaskOperatorVo paramVo) {
    personTaskService.addOperators(paramVo.getTaskCode(), paramVo.getOperatorList());
    return success("添加成功！");
  }

  @ApiOperation("人员检测任务添加被检测人员")
  @PostMapping("/add-details")
  public AlsaceResult<String> addDetails(@RequestBody PersonTaskDetailVo paramVo) {
    personTaskService.addDetails(paramVo.getTaskCode(), paramVo.getDetailList());
    return success("添加成功！");
  }

  @ApiOperation("下发人员检测任务")
  @PostMapping("/assign")
  public AlsaceResult<String> assign(@RequestBody List<String> taskCodeList) {
    personTaskService.assign(taskCodeList);
    return success("下发成功！");
  }

  @ApiOperation("人员检测任务详情、检测机构、检测地点和检测人员")
  @PostMapping("/detail/{taskCode}")
  public AlsaceResult<PersonTaskVo> detail(@PathVariable String taskCode) {
    Assert.hasLength(taskCode, "任务编码为空！");
    PersonTask queryTask = new PersonTask();
    queryTask.setTaskCode(taskCode).setDeleted(false);
    PersonTask task = personTaskService.findOne(queryTask);
    Assert.notNull(task, "检测任务不存在！");
    PersonTaskOrg queryOrg = new PersonTaskOrg();
    queryOrg.setTaskCode(taskCode).setDeleted(false);
    //检测机构
    List<PersonTaskOrg> orgList = personTaskOrgService.findAll(queryOrg);
    //地点
    PersonTaskLocation queryLocation = new PersonTaskLocation();
    queryLocation.setTaskCode(taskCode).setDeleted(false);
    List<PersonTaskLocation> locationList = personTaskLocationService.findAll(queryLocation);
    //地点对应的检测人员
    locationList.forEach(location-> location.setOperatorList(personTaskOperatorService.findAllUserInfo(taskCode,location.getId())));
    PersonTaskVo res = new PersonTaskVo();
    res.setTask(task).setLocationList(locationList).setOrgList(orgList);
    return success(res);
  }

  @ApiOperation("按照任务编码更新检测结果")
  @PostMapping("/result/batch")
  public AlsaceResult<String> resultBatch(@RequestBody @Validated TaskResultBatchVo param) {
    personTaskDetailService.updateResultBatch(param.getTaskCodeList(), param.getPositive());
    return success("更新成功！", null);
  }

  @ApiOperation("按照任务试管更新检测结果")
  @PostMapping("/result/single")
  public AlsaceResult<String> resultSingle(@RequestBody @Validated TaskResultSingleVo param) {
    PersonTask queryTask = new PersonTask();
    queryTask.setTaskCode(param.getTaskCode()).setDeleted(false);
    PersonTask task = personTaskService.findOne(queryTask);
    Assert.state(task != null, "任务不存在！");
    personTaskDetailService.updateResultSingle(param.getTaskCode(), param.getTestTubeNo(), param.getDetectionMethod(), param.getPositive());
    return success("更新成功！", null);
  }

}

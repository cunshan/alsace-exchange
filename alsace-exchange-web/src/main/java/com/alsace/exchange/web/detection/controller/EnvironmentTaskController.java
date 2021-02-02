package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.EnvironmentTask;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOrg;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskTag;
import com.alsace.exchange.service.detection.service.EnvironmentTaskOperatorService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskOrgService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskTagService;
import com.alsace.exchange.web.detection.vo.EnvironmentTaskDetailVo;
import com.alsace.exchange.web.detection.vo.EnvironmentTaskOperatorVo;
import com.alsace.exchange.web.detection.vo.EnvironmentTaskVo;
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

@Api(tags = "环境监测任务", value = "environmentTask")
@RestController
@RequestMapping("/env-task/task")
public class EnvironmentTaskController extends BaseController {

  @Resource
  private EnvironmentTaskService environmentTaskService;
  @Resource
  private EnvironmentTaskOrgService environmentTaskOrgService;
  @Resource
  private EnvironmentTaskTagService environmentTaskTagService;
  @Resource
  private EnvironmentTaskOperatorService environmentTaskOperatorService;


  @ApiOperation("环境监测任务保存")
  @PostMapping("/save")
  public AlsaceResult<EnvironmentTask> save(@RequestBody EnvironmentTaskVo param) {
    return success(environmentTaskService.save(param.getTask(), param.getOrgList()));
  }

  @ApiOperation("环境监测任务分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<EnvironmentTask>> page(@RequestBody PageParam<EnvironmentTask> param) {
    PageResult<EnvironmentTask> page = environmentTaskService.findPage(param);
    return success(page);
  }

  @ApiOperation("环境监测任务更新")
  @PostMapping("/update")
  public AlsaceResult<EnvironmentTask> update(@RequestBody EnvironmentTaskVo param) {
    EnvironmentTask domain = environmentTaskService.update(param.getTask(), param.getOrgList());
    return success(domain);
  }

  @ApiOperation("环境监测任务删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    environmentTaskService.delete(idList);
    return success("删除成功", null);
  }

  @ApiOperation("环境监测任务添加检测人员")
  @PostMapping("/add-operators")
  public AlsaceResult<String> addOperators(@RequestBody EnvironmentTaskOperatorVo paramVo) {
    environmentTaskService.addOperators(paramVo.getTaskCode(), paramVo.getOperatorList());
    return success("添加成功！");
  }

  @ApiOperation("环境监测任务添加被检测环境")
  @PostMapping("/add-details")
  public AlsaceResult<String> addDetails(@RequestBody EnvironmentTaskDetailVo paramVo) {
    environmentTaskService.addDetails(paramVo.getTaskCode(), paramVo.getDetailList());
    return success("添加成功！");
  }

  @ApiOperation("下发环境监测任务")
  @PostMapping("/assign")
  public AlsaceResult<String> assign(@RequestBody List<String> taskCodeList) {
    environmentTaskService.assign(taskCodeList);
    return success("下发成功！");
  }

  @ApiOperation("环境监测任务详情、检测机构和检测地点")
  @PostMapping("/detail/{taskCode}")
  public AlsaceResult<EnvironmentTaskVo> orgAndLocation(@PathVariable String taskCode) {
    Assert.hasLength(taskCode, "");
    EnvironmentTask queryTask = new EnvironmentTask();
    queryTask.setTaskCode(taskCode).setDeleted(false);
    EnvironmentTask task = environmentTaskService.findOne(queryTask);
    Assert.notNull(task, "检测任务不存在！");
    EnvironmentTaskOrg queryOrg = new EnvironmentTaskOrg();
    queryOrg.setTaskCode(taskCode).setDeleted(false);
    List<EnvironmentTaskOrg> orgList = environmentTaskOrgService.findAll(queryOrg);
    EnvironmentTaskTag queryLocation = new EnvironmentTaskTag();
    queryLocation.setTaskCode(taskCode).setDeleted(false);
    List<EnvironmentTaskTag> locationList = environmentTaskTagService.findAll(queryLocation);
    //地点对应的检测人员
    locationList.forEach(location-> location.setOperatorList(environmentTaskOperatorService.findAllUserInfo(taskCode,location.getId())));
    EnvironmentTaskVo res = new EnvironmentTaskVo();
    res.setTask(task).setTagList(locationList).setOrgList(orgList);
    return success(res);
  }

}

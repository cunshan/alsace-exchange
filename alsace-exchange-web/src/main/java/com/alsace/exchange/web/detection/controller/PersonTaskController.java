package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.service.PersonTaskService;
import com.alsace.exchange.web.detection.vo.PersonTaskDetailVo;
import com.alsace.exchange.web.detection.vo.PersonTaskOperatorVo;
import com.alsace.exchange.web.detection.vo.PersonTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "人员检测任务",value = "personTask")
@RestController
@RequestMapping("/person-task/")
public class PersonTaskController extends BaseController {

  @Resource
  private PersonTaskService personTaskService;


  @ApiOperation("人员检测任务保存")
  @PostMapping("/save")
  public AlsaceResult<PersonTask> save(@RequestBody PersonTaskVo param) {
    return success(personTaskService.save(param.getTask(),param.getOrgList(),param.getLocationList()));
  }

  @ApiOperation("人员检测任务分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<PersonTask>> page(@RequestBody PageParam<PersonTask> param) {
    PageResult<PersonTask> page = personTaskService.findPage(param);
    return success(page);
  }

  @ApiOperation("人员检测任务更新")
  @PostMapping("/update")
  public AlsaceResult<PersonTask> update(@RequestBody PersonTask param) {
    PersonTask domain = personTaskService.update(param);
    return success(domain);
  }

  @ApiOperation("人员检测任务删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    personTaskService.delete(idList);
    return success("删除成功",null);
  }

  @ApiOperation("人员检测任务添加检测人员")
  @PostMapping("/add-operators")
  public AlsaceResult<String> addOperators(@RequestBody PersonTaskOperatorVo paramVo){
    personTaskService.addOperators(paramVo.getTaskCode(),paramVo.getOperatorList());
    return success("添加成功！");
  }

  @ApiOperation("人员检测任务添加被检测人员")
  @PostMapping("/add-details")
  public AlsaceResult<String> addDetails(@RequestBody PersonTaskDetailVo paramVo){
    personTaskService.addDetails(paramVo.getTaskCode(),paramVo.getDetailList());
    return success("添加成功！");
  }
  
}

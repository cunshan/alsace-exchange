package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import com.alsace.exchange.service.detection.service.PersonTaskService;
import com.alsace.exchange.web.detection.vo.PersonTaskDetailVo;
import com.alsace.exchange.web.detection.vo.PersonTaskOperatorVo;
import com.alsace.exchange.web.detection.vo.PersonTaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Queue;

@Api(tags = "人员检测任务明细",value = "personTaskDetail")
@RestController
@RequestMapping("/person-task/detail")
public class PersonTaskDetailController extends BaseController {

  @Resource
  private PersonTaskDetailService personTaskDetailService;
  @Resource
  private PersonTaskService personTaskService;


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

  @ApiOperation("获取单条人员检测任务明细")
  @PostMapping("/get-one")
  public AlsaceResult<PersonTaskDetail> detail(@RequestBody PersonTaskDetail param){
    param.setDeleted(false);
    PersonTaskDetail detail = personTaskDetailService.findOne(param);
    if(detail == null){
      throw new AlsaceException("被检测人员信息不存在！");
    }
    return success(detail);
  }

  @ApiOperation("提交检测明细")
  @PostMapping("/submit")
  public AlsaceResult<String> submit(@RequestBody PersonTaskDetail param){
    personTaskService.submit(param);
    return success("提交成功",null);
  }

}

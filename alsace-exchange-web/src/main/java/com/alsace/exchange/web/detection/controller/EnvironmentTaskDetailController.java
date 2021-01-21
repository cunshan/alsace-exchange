package com.alsace.exchange.web.detection.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.emums.TaskDetailStatus;
import com.alsace.exchange.service.detection.service.EnvironmentTaskDetailService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "环境监测任务明细",value = "environmentTaskDetail")
@RestController
@RequestMapping("/env-task/detail")
public class EnvironmentTaskDetailController extends BaseController {

  @Resource
  private EnvironmentTaskDetailService environmentTaskDetailService;
  @Resource
  private OrderNoGenerator orderNoGenerator;

  @ApiOperation("环境监测任务明细分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<EnvironmentTaskDetail>> page(@RequestBody PageParam<EnvironmentTaskDetail> param) {
    PageResult<EnvironmentTaskDetail> page = environmentTaskDetailService.findPage(param);
    return success(page);
  }

  @ApiOperation("环境监测任务明细更新")
  @PostMapping("/update")
  public AlsaceResult<EnvironmentTaskDetail> update(@RequestBody EnvironmentTaskDetail param) {
    EnvironmentTaskDetail domain = environmentTaskDetailService.update(param);
    return success(domain);
  }

  @ApiOperation("环境监测任务明细删除")
  @PostMapping("/delete")
  public AlsaceResult<String> delete(@RequestBody List<Long> idList) {
    environmentTaskDetailService.delete(idList);
    return success("删除成功",null);
  }

  @ApiOperation("环境检测任务明细保存")
  @PostMapping("/save")
  public AlsaceResult<EnvironmentTaskDetail> save(@RequestBody EnvironmentTaskDetail param) {
    String detailCode =orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.ENVIRONMENT_TASK_DETAIL_CODE);
    param.setDetailCode(detailCode);
    param.setDetailStatus(TaskDetailStatus.INIT.status());
    return success(environmentTaskDetailService.save(param));
  }

}

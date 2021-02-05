package com.alsace.exchange.web.sys.controller;

import com.alsace.exchange.common.base.AlsaceResult;
import com.alsace.exchange.common.base.BaseController;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.sys.domain.DictType;
import com.alsace.exchange.service.sys.domain.OperationLog;
import com.alsace.exchange.service.sys.service.DictTypeService;
import com.alsace.exchange.service.sys.service.OperationLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "操作日志",value = "log")
@RestController
@RequestMapping("/log")
public class OperationLogController extends BaseController {
  
  @Resource
  private OperationLogService operationLogService;

  @ApiOperation("操作日志分页查询")
  @PostMapping("/page")
  public AlsaceResult<PageResult<OperationLog>> page(@RequestBody PageParam<OperationLog> param) {
    PageResult<OperationLog> page = operationLogService.findPage(param);
    return success(page);
  }
  
}

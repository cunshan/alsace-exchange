package com.alsace.exchange.web.detection.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TaskResultBatchVo {

  @ApiModelProperty(value = "任务编码列表", required = true)
  @NotEmpty(message = "任务编码为空！")
  private List<String> taskCodeList;

  @ApiModelProperty(value = "是否阳性", required = true)
  @NotNull(message = "是否阳性为空！")
  private Boolean positive;

}

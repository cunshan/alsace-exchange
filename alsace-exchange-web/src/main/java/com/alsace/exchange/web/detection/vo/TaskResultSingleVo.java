package com.alsace.exchange.web.detection.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TaskResultSingleVo {

  @ApiModelProperty(value = "任务编码", required = true)
  @NotEmpty(message = "任务编码为空！")
  private String taskCode;

  @ApiModelProperty(value = "试管编码", required = true)
  @NotEmpty(message = "试管编码为空！")
  private String testTubeNo;

  @ApiModelProperty(value = "是否阳性", required = true)
  @NotNull(message = "是否阳性为空！")
  private Boolean positive;

  @ApiModelProperty(value = "是否阳性", required = true)
  @NotNull(message = "是否阳性为空！")
  private Integer detectionMethod;

}

package com.alsace.exchange.web.detection.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EnvironmentTaskParam {

  @ApiModelProperty(value = "任务编码")
  private String taskCode;

  @ApiModelProperty(value = "任务名称")
  private String taskName;

  @ApiModelProperty(value = "任务状态 10：已创建 20：待下发 30：已下发 40：待开始 50：进行中 60：已完成 70：取消")
  private Integer taskStatus;

}

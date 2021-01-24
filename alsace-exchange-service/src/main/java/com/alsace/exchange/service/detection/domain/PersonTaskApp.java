package com.alsace.exchange.service.detection.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class PersonTaskApp implements Serializable {

  private static final long serialVersionUID = -1183139041296312813L;

  @ApiModelProperty(value = "任务编码")
  private String taskCode;

  @ApiModelProperty(value = "任务名称")
  private String taskName;

  @ApiModelProperty(value = "任务描述")
  private String taskDesc;

  @ApiModelProperty(value = "任务状态 10：已创建 20：待下发 30：已下发 40：待开始 50：进行中 60：已完成 70：取消")
  private Integer taskStatus;

  @ApiModelProperty(value = "任务开始时间")
  private Date startDate;

  @ApiModelProperty(value = "任务结束时间")
  private Date endDate;

  @ApiModelProperty(value = "检测类型 1 全民检测  2 非全民检测")
  private Integer detectionType;

  @ApiModelProperty(value = "检测项目 存储为以下格式[{\"code\":\"1\",\"name\":\"核酸检测\"}]")
  private String detectionMethod;

  @ApiModelProperty(value = "混管设置")
  private String mixedMode;

  @ApiModelProperty(value = "地点ID")
  private Long locationId;

  @ApiModelProperty(value = "地点名称")
  private String locationName;

}

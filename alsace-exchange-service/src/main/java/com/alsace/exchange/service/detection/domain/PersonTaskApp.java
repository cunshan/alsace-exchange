package com.alsace.exchange.service.detection.domain;

import com.alsace.exchange.common.validate.groups.Create;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class PersonTaskApp implements Serializable {

  private static final long serialVersionUID = -1183139041296312813L;

  @ApiModelProperty(value = "任务编码")
  private String taskCode;

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

  @ApiModelProperty(value = "检测项目")
  private String detectionMethod;

  @ApiModelProperty(value = "混管设置")
  private String mixedMode;

  @ApiModelProperty(value = "地点ID")
  private Long locationId;

  @ApiModelProperty(value = "地点名称")
  private String locationName;

}

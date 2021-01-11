package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.service.detection.domain.EnvironmentTask;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskTag;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOrg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class EnvironmentTaskVo implements Serializable {
  private static final long serialVersionUID = 5482783309755426528L;

  @ApiModelProperty(value = "任务主要信息")
  private EnvironmentTask task;

  @ApiModelProperty(value = "检测地点列表")
  private List<EnvironmentTaskTag> tagList;

  @ApiModelProperty(value = "检测机构列表")
  private List<EnvironmentTaskOrg> orgList;

}

package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PersonTaskVo implements Serializable {
  private static final long serialVersionUID = 5482783309755426528L;

  @ApiModelProperty(value = "任务主要信息")
  private PersonTask task;

  @ApiModelProperty(value = "检测地点列表")
  private List<PersonTaskLocation> locationList;

  @ApiModelProperty(value = "检测地点列表")
  private List<PersonTaskOrg> orgList;

}

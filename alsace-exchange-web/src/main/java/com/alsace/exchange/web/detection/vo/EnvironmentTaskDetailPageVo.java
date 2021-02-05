package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class EnvironmentTaskDetailPageVo implements Serializable {
  private static final long serialVersionUID = 1496080653239245711L;

  @ApiModelProperty(value = "检测任务号", required = true)
  private String taskCode;

  @ApiModelProperty(value = "检测明细")
  private List<EnvironmentTaskDetail> detailList;

}

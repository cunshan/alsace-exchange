package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.PersonDetectionMethodCount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PersonTaskDetailPageVo implements Serializable {
  private static final long serialVersionUID = 1496080653239245711L;

  @ApiModelProperty(value = "检测任务号", required = true)
  @NotBlank(message = "任务号为空！")
  private String taskCode;

  @ApiModelProperty(value = "检测任务表单号", required = true)
  @NotBlank(message = "检测任务表单号为空！")
  private String formCode;

  @ApiModelProperty(value = "检测总人数")
  private long personCount;

  @ApiModelProperty("检测项目对应统计数")
  private List<PersonDetectionMethodCount> methodList;

  @ApiModelProperty(value = "检测明细分页明细")
  private PageResult<PersonTaskDetailPageDetailVo> detailPage;


}

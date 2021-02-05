package com.alsace.exchange.web.detection.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel
public class EnvironmentTaskDetailPageParamVo implements Serializable {
  private static final long serialVersionUID = 1496080653239245711L;

  @ApiModelProperty(value = "检测任务号", required = true)
  @NotBlank(message = "任务号为空！")
  private String taskCode;

  @ApiModelProperty(value = "检测状态  10:创建 20:已提交")
  private Integer detailStatus;

  @ApiModelProperty(value = "表单状态 10：进行中 20：已完成")
  private Integer formStatus;

  @ApiModelProperty(value = "商户名称或者税号")
  private String companyNameOrTaxCode;
}

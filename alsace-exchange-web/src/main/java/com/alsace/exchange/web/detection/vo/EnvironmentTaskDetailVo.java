package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class EnvironmentTaskDetailVo implements Serializable {
  private static final long serialVersionUID = -5643199016424432448L;

  @ApiModelProperty(value = "检测任务号", required = true)
  @NotBlank(message = "任务号为空！")
  private String taskCode;


  @ApiModelProperty(value = "被检测人员名单", required = true)
  @NotEmpty(message = "被检测人员名单为空！")
  private List<EnvironmentTaskDetail> detailList;

}

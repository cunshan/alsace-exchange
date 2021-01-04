package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class PersonTaskDetailVo implements Serializable {
  private static final long serialVersionUID = -5643199016424432448L;

  @ApiModelProperty("检测任务号")
  @NotBlank(message = "任务号为空！")
  private String taskCode;


  @ApiModelProperty("被检测人员名单")
  @NotEmpty(message = "被检测人员名单为空！")
  private List<PersonTaskDetail> detailList;

}

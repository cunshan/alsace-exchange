package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonTaskDetailResultVo implements Serializable {

  private static final long serialVersionUID = 367893305710249314L;

  @ApiModelProperty("试管列表")
  private List<PersonTaskDetailResult> testTubeList;

  @ApiModelProperty("检测日期")
  private String detectionDate;
}

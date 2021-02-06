package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel
public class PersonTaskDetailPageDetailVo implements Serializable {
  private static final long serialVersionUID = -446287740159515017L;

  @ApiModelProperty("姓名")
  private String personName;
  @ApiModelProperty("身份证")
  private String idCardNo;
  @ApiModelProperty("试管列表")
  private List<PersonTaskDetailResult> testTubeList;
}

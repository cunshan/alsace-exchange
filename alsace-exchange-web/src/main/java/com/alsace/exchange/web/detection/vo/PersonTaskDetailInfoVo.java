package com.alsace.exchange.web.detection.vo;

import com.alsace.exchange.common.base.PageResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel
public class PersonTaskDetailInfoVo implements Serializable {

  private static final long serialVersionUID = 367893305710249314L;

  @ApiModelProperty("姓名")
  private String personName;

  @ApiModelProperty("电话")
  private String tel;

  @ApiModelProperty("试管列表")
  PageResult<PersonTaskDetailResultVo> resultPage;

}

package com.alsace.exchange.web.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("APP手机验证码登录")
@Data
public class AppLoginVo implements Serializable {
  private static final long serialVersionUID = 3809110669346951870L;

  @ApiModelProperty("手机号")
  private String mobile;

  @ApiModelProperty("验证码")
  private String checkCode;

}

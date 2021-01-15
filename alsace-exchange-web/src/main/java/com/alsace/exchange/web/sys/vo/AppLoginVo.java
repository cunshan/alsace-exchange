package com.alsace.exchange.web.sys.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ApiModel("APP手机验证码登录")
@Data
public class AppLoginVo implements Serializable {
  private static final long serialVersionUID = 3809110669346951870L;

  @ApiModelProperty(value = "手机号",required = true)
  @NotBlank(message = "手机号为空！")
  private String mobile;

  @ApiModelProperty(value = "验证码",required = true)
  @NotBlank(message = "验证码为空！")
  private String checkCode;

}

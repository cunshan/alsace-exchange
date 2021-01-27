package com.alsace.exchange.web.sys.vo;

import com.alsace.exchange.service.sys.domain.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@ApiModel("用户登录返回结果")
@Data
@AllArgsConstructor
public class LoginVo implements Serializable {
  private static final long serialVersionUID = -2735578285638345920L;

  @ApiModelProperty("token")
  private String token;
  @ApiModelProperty("用户登录名")
  private String userName;
  @ApiModelProperty("用户昵称")
  private String nickName;

}

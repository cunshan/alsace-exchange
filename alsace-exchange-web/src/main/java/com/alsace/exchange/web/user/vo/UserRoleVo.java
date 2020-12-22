package com.alsace.exchange.web.user.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRoleVo implements Serializable {
  private static final long serialVersionUID = 2585098077649887958L;
  
  private String loginAccount;
  
  private List<String> roleList;
  
}

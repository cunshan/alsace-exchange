package com.alsace.exchange.web.sys.vo;

import com.alsace.exchange.service.sys.domain.UserData;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDataVo implements Serializable {
  private static final long serialVersionUID = 2585098077649887958L;
  
  private String loginAccount;
  
  private List<UserData> dataList;
  
}

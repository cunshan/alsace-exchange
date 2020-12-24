package com.alsace.exchange.web.sys.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色和菜单关系VO
 */
@Data
public class RoleMenuVo implements Serializable {
  private static final long serialVersionUID = -6821614339487370531L;

  /**
   * 角色编码
   */
  private String roleCode;

  /**
   * 角色对应菜单ID
   */
  private List<Long> menuIdList;

}

package com.alsace.exchange.web.user.vo;

import com.alsace.exchange.service.user.domain.Menu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树形结构
 */
@Data
@Accessors(chain = true)
public class MenuTreeVo implements Serializable {

  private static final long serialVersionUID = 4535754529966042382L;

  private Menu menu;

  private List<MenuTreeVo> children;


  public MenuTreeVo(Menu menu) {
    this.menu = menu;
  }

}

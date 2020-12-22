package com.alsace.exchange.web.user.biz;

import com.alsace.exchange.common.utils.JsonUtils;
import com.alsace.exchange.service.user.domain.Menu;
import com.alsace.exchange.service.user.service.MenuService;
import com.alsace.exchange.web.user.vo.MenuTreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MenuHandler {

  @Resource
  private MenuService menuService;

  /**
   * 获取菜单树形结构
   */
  public List<MenuTreeVo> tree() {
    Menu menuParam = new Menu();
    menuParam.setDeleted(false);
    List<Menu> menuList = menuService.findAll(menuParam);
    //有父节点的菜单
    Map<Long,List<MenuTreeVo>> childrenMap = new HashMap<>();
    //菜单根节点
    List<MenuTreeVo> rootList = new ArrayList<>();
    menuList.forEach(menu -> {
      if (menu.getParentId() == null) {
        rootList.add(new MenuTreeVo(menu));
      } else {
        List<MenuTreeVo> temp = childrenMap.getOrDefault(menu.getParentId(),new ArrayList<>());
        temp.add(new MenuTreeVo(menu));
        childrenMap.putIfAbsent(menu.getParentId(),temp);
      }
    });
    rootList.forEach(menu-> addChildren(menu,childrenMap));
    log.info(JsonUtils.toJson(rootList));
    return rootList;

  }

  /**
   * 添加子节点
   */
  private void addChildren(MenuTreeVo root, Map<Long, List<MenuTreeVo>> childrenMap) {
    root.setChildren(childrenMap.get(root.getMenu().getId()));
    if(root.getChildren()==null){
      return;
    }
    root.getChildren().forEach(menu->addChildren(menu,childrenMap));
  }


}

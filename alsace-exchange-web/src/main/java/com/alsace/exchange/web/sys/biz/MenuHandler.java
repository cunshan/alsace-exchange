package com.alsace.exchange.web.sys.biz;

import com.alsace.exchange.common.base.TreeVo;
import com.alsace.exchange.common.utils.JsonUtils;
import com.alsace.exchange.service.sys.domain.Menu;
import com.alsace.exchange.service.sys.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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
  public List<TreeVo<Menu>> tree(Long rootId) {
    Menu menuParam = new Menu();
    menuParam.setDeleted(false);
    List<Menu> menuList = menuService.findAll(menuParam);
    List<TreeVo<Menu>> rootList = buildTree(menuList,rootId);
    log.info(JsonUtils.toJson(rootList));
    return rootList;

  }

  private List<TreeVo<Menu>> buildTree(List<Menu> menuList, Long rootId) {
    //有父节点的菜单
    Map<Long, List<TreeVo<Menu>>> childrenMap = new HashMap<>();
    //菜单根节点
    List<TreeVo<Menu>> rootList = new ArrayList<>();
    menuList.forEach(menu -> {
      if (menu.getParentId() == null) {
        if (rootId == null) {
          rootList.add(new TreeVo<>(menu));
        } else if (rootId.compareTo(menu.getId()) == 0) {
          rootList.add(new TreeVo<>(menu));
        }
      } else {
        List<TreeVo<Menu>> temp = childrenMap.getOrDefault(menu.getParentId(), new ArrayList<>());
        temp.add(new TreeVo<>(menu));
        childrenMap.putIfAbsent(menu.getParentId(), temp);
      }
    });
    rootList.forEach(menu -> addChildren(menu, childrenMap));
    return rootList;
  }

  /**
   * 添加子节点
   */
  private void addChildren(TreeVo<Menu> root, Map<Long, List<TreeVo<Menu>>> childrenMap) {
    root.setChildren(childrenMap.get(root.getValue().getId()));
    if (root.getChildren() == null) {
      return;
    }
    root.getChildren().forEach(menu -> addChildren(menu, childrenMap));
  }

  /**
   * 根据角色码获取菜单树
   */
  public List<TreeVo<Menu>> queryByRole(String roleCode) {
    List<Menu> menuList = menuService.findByRoleCode(Collections.singletonList(roleCode));
    return buildTree(menuList,null);
  }

  /**
   * 根据登录账号获取对应的菜单信息
   * @param loginAccount 登录账号
   */
  public List<TreeVo<Menu>> queryByLoginAccount(String loginAccount) {
    List<Menu> menuList = menuService.findByLoginAccount(loginAccount);
    return buildTree(menuList,null);

  }
}

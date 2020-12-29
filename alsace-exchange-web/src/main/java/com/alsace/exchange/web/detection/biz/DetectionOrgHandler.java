package com.alsace.exchange.web.detection.biz;

import com.alsace.exchange.common.base.TreeVo;
import com.alsace.exchange.common.utils.JsonUtils;
import com.alsace.exchange.service.detection.domain.DetectionOrg;
import com.alsace.exchange.service.detection.service.DetectionOrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class DetectionOrgHandler {

  @Resource
  private DetectionOrgService detectionOrgService;


  /**
   * 获取菜单树形结构
   */
  public List<TreeVo<DetectionOrg>> tree(String rootCode) {
    DetectionOrg queryParam = new DetectionOrg();
    queryParam.setDeleted(false);
    List<DetectionOrg> allList = detectionOrgService.findAll(queryParam);
    //有父节点的菜单
    Map<String, List<TreeVo<DetectionOrg>>> childrenMap = new HashMap<>();
    //菜单根节点
    List<TreeVo<DetectionOrg>> rootList = new ArrayList<>();
    allList.forEach(org -> {
      if (StringUtils.isBlank(org.getParentOrgCode())) {
        if (StringUtils.isBlank(rootCode)) {
          rootList.add(new TreeVo<>(org));
        } else if (rootCode.equals(org.getOrgCode())) {
          rootList.add(new TreeVo<>(org));
        }
      } else {
        List<TreeVo<DetectionOrg>> temp = childrenMap.getOrDefault(org.getParentOrgCode(), new ArrayList<>());
        temp.add(new TreeVo<>(org));
        childrenMap.putIfAbsent(org.getParentOrgCode(), temp);
      }
    });
    rootList.forEach(menu -> addChildren(menu, childrenMap));
    log.info(JsonUtils.toJson(rootList));
    return rootList;

  }

  /**
   * 添加子节点
   */
  private void addChildren(TreeVo<DetectionOrg> root, Map<String, List<TreeVo<DetectionOrg>>> childrenMap) {
    root.setChildren(childrenMap.get(root.getValue().getOrgCode()));
    if (root.getChildren() == null) {
      return;
    }
    root.getChildren().forEach(menu -> addChildren(menu, childrenMap));
  }

}

package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOrg;

public interface EnvironmentTaskOrgService extends BaseService<EnvironmentTaskOrg,Long> {

  /**
   * 根据任务编码删除检测机构
   */
  void deleteByTaskCode(String taskCode);
}

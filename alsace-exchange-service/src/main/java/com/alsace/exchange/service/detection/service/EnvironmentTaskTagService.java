package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskTag;

public interface EnvironmentTaskTagService extends BaseService<EnvironmentTaskTag,Long> {

  /**
   * 根据任务编码删除检测地点
   */
  void deleteByTaskCode(String taskCode);
}

package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;

public interface PersonTaskOrgService extends BaseService<PersonTaskOrg,Long> {

  /**
   * 根据任务编码删除检测机构
   */
  void deleteByTaskCode(String taskCode);
}

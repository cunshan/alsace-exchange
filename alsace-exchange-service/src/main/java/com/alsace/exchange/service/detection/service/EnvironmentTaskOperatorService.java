package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOperator;
import com.alsace.exchange.service.sys.domain.User;

import java.util.List;

public interface EnvironmentTaskOperatorService extends BaseService<EnvironmentTaskOperator,Long> {

  /**
   * 根据任务编码和tag ID获取所有检测人员信息
   * @param taskCode 任务编码
   * @param tagId 地点ID
   */
  List<User> findAllUserInfo(String taskCode, Long tagId);
}

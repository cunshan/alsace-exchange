package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.PersonTaskOperator;
import com.alsace.exchange.service.sys.domain.User;

import java.util.List;

public interface PersonTaskOperatorService extends BaseService<PersonTaskOperator,Long> {

  /**
   * 根据任务编码和地点ID获取所有检测人员信息
   * @param taskCode 任务编码
   * @param locationId 地点ID
   * @return
   */
  List<User> findAllUserInfo(String taskCode, Long locationId);
}

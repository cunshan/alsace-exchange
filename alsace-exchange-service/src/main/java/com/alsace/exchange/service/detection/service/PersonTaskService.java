package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.common.validate.groups.Create;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import com.alsace.exchange.service.detection.domain.PersonTaskOperator;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface PersonTaskService extends BaseService<PersonTask,Long> {

  /**
   * 保存人员检测任务
   * @param task 任务主要信息
   * @param orgList 任务检测机构
   * @param locationList 任务地点
   */
  PersonTask save(@Validated(Create.class) PersonTask task, @Validated(Create.class) List<PersonTaskOrg> orgList, @Validated(Create.class) List<PersonTaskLocation> locationList);

  /**
   * 检测任务添加检测人员
   * @param taskCode 检测任务号
   * @param operatorList 检测人员
   */
  void addOperators(String taskCode, List<PersonTaskOperator> operatorList);
}

package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskApp;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import com.alsace.exchange.service.detection.domain.PersonTaskOperator;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface PersonTaskService extends BaseService<PersonTask,Long> {

  /**
   * 保存人员检测任务
   * @param task 任务主要信息
   * @param orgList 任务检测机构
   * @param locationList 任务地点
   */
  PersonTask save(PersonTask task, List<PersonTaskOrg> orgList, List<PersonTaskLocation> locationList);

  /**
   * 更新人员检测任务
   * @param task 任务主要信息
   * @param orgList 任务检测机构
   * @param locationList 任务地点
   */
  PersonTask update(PersonTask task, List<PersonTaskOrg> orgList, List<PersonTaskLocation> locationList);

  /**
   * 检测任务添加检测人员
   * @param taskCode 检测任务号
   * @param operatorList 检测人员
   */
  void addOperators(String taskCode, List<PersonTaskOperator> operatorList);

  /**
   * 检测任务添加被检测人员
   * @param taskCode 检测任务号
   * @param detailList 被检测人员
   */
  void addDetails(String taskCode, List<PersonTaskDetail> detailList);

  /**
   * 开始检测任务
   * @param taskCode 任务编码
   * @return 开始的表单
   */
  PersonTaskForm startTask(String taskCode);

  /**
   * 提交个人任务检测明细
   */
  void submitDetail(PersonTaskDetail param);

  /**
   * APP获取登录人任务
   */
  PageResult<PersonTaskApp> findPersonTaskApp(PageParam<PersonTaskApp> param);

  /**
   * 下发人员检测任务
   */
  void assign(@NotEmpty(message = "任务编码列表为空！") List<String> taskCodeList);

  /**
   * 批量更新任务结果
   */
  void updateResultBatch(List<String> taskCodeList, Boolean positive);
}

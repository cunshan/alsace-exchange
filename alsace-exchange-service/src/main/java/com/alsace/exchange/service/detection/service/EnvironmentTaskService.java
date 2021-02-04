package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.validate.groups.Create;
import com.alsace.exchange.service.detection.domain.EnvironmentTask;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskForm;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOperator;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOrg;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface EnvironmentTaskService extends BaseService<EnvironmentTask,Long> {

  /**
   * 保存人员检测任务
   * @param task 任务主要信息
   * @param orgList 任务检测机构
   */
  EnvironmentTask save(@Validated(Create.class) EnvironmentTask task, @Validated(Create.class) List<EnvironmentTaskOrg> orgList);

  /**
   * 更新人员检测任务
   * @param task 任务主要信息
   * @param orgList 任务检测机构
   */
  EnvironmentTask update(EnvironmentTask task, List<EnvironmentTaskOrg> orgList);

  /**
   * 检测任务添加检测人员
   * @param taskCode 检测任务号
   * @param operatorList 检测人员
   */
  void addOperators(String taskCode, List<EnvironmentTaskOperator> operatorList);

  /**
   * 检测任务添加被检测人员
   * @param taskCode 检测任务号
   * @param detailList 被检测人员
   */
  void addDetails(String taskCode, List<EnvironmentTaskDetail> detailList);

  /**
   * 开始检测任务
   * @param taskCode 任务编码
   * @return 开始的表单
   */
  EnvironmentTaskForm startTask(String taskCode);

  /**
   * 提交个人任务检测明细
   */
  void submitDetail(EnvironmentTaskDetail param);

  /**
   * APP获取登录人任务
   */
  PageResult<EnvironmentTask> findEnvironmentTaskApp(PageParam<EnvironmentTask> param);

  /**
   * 下发人员检测任务
   */
  void assign(List<String> taskCodeList);

}

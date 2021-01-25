package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailResult;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;

import java.util.List;

public interface EnvironmentTaskDetailService extends BaseService<EnvironmentTaskDetail,Long> {

  /**
   * 根据明细编码删除样本
   */
  void deleteResultByDetailCode(String detailCode);

  /**
   * 保存检测样本
   */
  void saveResult(List<EnvironmentTaskDetailResult> resultList);

  /**
   * 导入被检测人员信息
   * @param taskCode 任务编码
   * @param param 导入内容
   * @return
   */
  List<EnvironmentTaskDetail> importDetails(List<Object> param, String taskCode);
}

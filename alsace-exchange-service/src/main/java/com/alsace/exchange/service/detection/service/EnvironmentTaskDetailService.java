package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailResult;

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
}

package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailImport;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailResult;
import com.itextpdf.text.DocumentException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
   * 导入被检测环境信息
   * @param taskCode 任务编码
   * @param param 导入内容
   * @return
   */
  List<EnvironmentTaskDetail> importDetails(List<Object> param, String taskCode);

  /**
   * 环境检测结果分页查询
   * @param param 分页查询条件
   * @Author: wayne
   * @Date: 2021/2/02
   */
  PageResult<EnvironmentTaskDetail> findResultPage(PageParam<EnvironmentTaskDetail> param);

  /**
   * 环境检测结果条件查询
   * @param param 分页查询条件
   * @Author: wayne
   * @Date: 2021/2/02
   */
  List<EnvironmentTaskDetailImport> findResults(EnvironmentTaskDetail param);

  /**
   * 导出PDF
   * @param taskCode 任务编号
   * @Author: wayne
   * @Date: 2021/2/02
   */
  ByteArrayOutputStream convertReceivePdf(EnvironmentTaskDetail param) throws IOException, DocumentException;

  /**
   * 保存明细
   * @param param 任务明细
   * @Author: wayne
   * @Date: 2021/2/02
   */
  EnvironmentTaskDetail saveDetail(EnvironmentTaskDetail param);

  /**
   * APP获取任务对应明细分页
   */
  PageResult<EnvironmentTaskDetail> findFormPage(PageParam<EnvironmentTaskDetail> queryDetail, Integer formStatus, String companyNameOrTaxCode);

  /**
   * 更新状态
   */
  void updateStatus(EnvironmentTaskDetail dbDetail);
}

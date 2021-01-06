package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;

import java.util.List;

public interface PersonTaskFormService extends BaseService<PersonTaskForm,Long> {


  /**
   * 提交人员检测表单
   * @param param
   */
  void submitForm(List<String> param);
}

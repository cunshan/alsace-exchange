package com.alsace.exchange.service.detection.service;

import com.alsace.exchange.common.base.BaseService;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;

public interface PersonTaskFormService extends BaseService<PersonTaskForm,Long> {


  /**
   * 提交人员检测表单
   */
  void submitForm(PersonTaskForm param);
}

package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import com.alsace.exchange.service.detection.emums.TaskFormStatus;
import com.alsace.exchange.service.detection.repositories.PersonTaskFormRepository;
import com.alsace.exchange.service.detection.service.PersonTaskFormService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class PersonTaskFormServiceImpl extends AbstractBaseServiceImpl<PersonTaskForm> implements PersonTaskFormService {

  @Resource
  private PersonTaskFormRepository personTaskFormRepository;


  @Override
  protected JpaRepository<PersonTaskForm, Long> getJpaRepository() {
    return this.personTaskFormRepository;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTaskForm> getJpaSpecificationExecutor() {
    return this.personTaskFormRepository;
  }

  @Override
  public void submitForm(PersonTaskForm param) {
    Assert.hasLength(param.getFormCode(), "任务表单编码为空！");
    //查询出任务
    PersonTaskForm queryForm = new PersonTaskForm();
    queryForm.setTaskCode(param.getFormCode())
        .setCreatedBy(getLoginAccount())
        .setDeleted(false);
    PersonTaskForm form = this.findOne(queryForm);
    if (form == null) {
      throw new AlsaceException("任务表单不存在！");
    }
    form.setFormStatus(TaskFormStatus.COMPLETED.status());
    this.personTaskFormRepository.saveAndFlush(form);
  }
}

package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskForm;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskForm;
import com.alsace.exchange.service.detection.emums.TaskFormStatus;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskFormRepository;
import com.alsace.exchange.service.detection.service.EnvironmentTaskFormService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EnvironmentTaskFormServiceImpl extends AbstractBaseServiceImpl<EnvironmentTaskForm> implements EnvironmentTaskFormService {

  @Resource
  private EnvironmentTaskFormRepository environmentTaskFormRepository;


  @Override
  protected JpaRepository<EnvironmentTaskForm, Long> getJpaRepository() {
    return this.environmentTaskFormRepository;
  }

  @Override
  protected JpaSpecificationExecutor<EnvironmentTaskForm> getJpaSpecificationExecutor() {
    return this.environmentTaskFormRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void submitForm(List<String> formCodeList) {
    Assert.notEmpty(formCodeList, "任务表单编码为空！");
    formCodeList.forEach(code -> {
      //查询出任务
      EnvironmentTaskForm queryForm = new EnvironmentTaskForm();
      queryForm.setFormCode(code)
          .setCreatedBy(getLoginAccount())
          .setDeleted(false);
      EnvironmentTaskForm form = this.findOne(queryForm);
      if (form == null) {
        throw new AlsaceException("任务表单不存在！");
      }
      if (TaskFormStatus.COMPLETED.status().equals(form.getFormStatus())) {
        throw new AlsaceException(String.format("表单%s已经提交，不能重复提交！", code));
      }
      form.setFormStatus(TaskFormStatus.COMPLETED.status());
      this.environmentTaskFormRepository.saveAndFlush(form);
    });

  }
}

package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import com.alsace.exchange.service.detection.domain.PersonTaskOperator;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;
import com.alsace.exchange.service.detection.emums.TaskStatus;
import com.alsace.exchange.service.detection.repositories.PersonTaskRepository;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import com.alsace.exchange.service.detection.service.PersonTaskLocationService;
import com.alsace.exchange.service.detection.service.PersonTaskOperatorService;
import com.alsace.exchange.service.detection.service.PersonTaskOrgService;
import com.alsace.exchange.service.detection.service.PersonTaskService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonTaskServiceImpl extends AbstractBaseServiceImpl<PersonTask> implements PersonTaskService {

  @Resource
  private PersonTaskRepository personTaskRepository;
  @Resource
  private PersonTaskOrgService personTaskOrgService;
  @Resource
  private PersonTaskLocationService personTaskLocationService;
  @Resource
  private PersonTaskOperatorService personTaskOperatorService;
  @Resource
  private PersonTaskDetailService personTaskDetailService;

  @Resource
  private OrderNoGenerator orderNoGenerator;

  @Override
  protected JpaRepository<PersonTask, Long> getJpaRepository() {
    return this.personTaskRepository;
  }

  @Override
  protected JpaSpecificationExecutor<PersonTask> getJpaSpecificationExecutor() {
    return this.personTaskRepository;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public PersonTask save(PersonTask task, List<PersonTaskOrg> orgList, List<PersonTaskLocation> locationList) {
    Assert.notNull(task, "任务信息为空！");
    Assert.notEmpty(orgList, "检测机构为空！");
    String taskCode = orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.PERSON_TASK_CODE);
    //保存任务对应检测机构
    orgList.forEach(org -> org.setTaskCode(taskCode));
    personTaskOrgService.saveBatch(orgList);
    //保存任务对应地点
    if (CollectionUtils.isEmpty(locationList)) {
      locationList = new ArrayList<>();
      locationList.add(new PersonTaskLocation().setLocationName("无地点要求"));
    }
    locationList.forEach(location -> location.setTaskCode(taskCode));
    personTaskLocationService.saveBatch(locationList);
    //保存任务
    task.setTaskCode(taskCode);
    task.setTaskStatus(TaskStatus.INIT.status());
    return this.save(task);
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void addOperators(String taskCode, List<PersonTaskOperator> operatorList) {
    //校验任务状态
    PersonTask taskParam = new PersonTask();
    taskParam.setTaskCode(taskCode).setDeleted(false);
    PersonTask task = this.findOne(taskParam);
    Assert.state(task != null, "检测任务不存在！");
    Assert.state(TaskStatus.ASSIGNED.status().equals(task.getTaskStatus()), String.format("检测任务状态不允许添加检测人员！[%s]", task.getTaskStatus()));
    //保存检测人员
    operatorList.forEach(operator -> operator.setTaskCode(taskCode));
    personTaskOperatorService.saveBatch(operatorList);
    //更改任务状态为待下发
    task.setTaskStatus(TaskStatus.READY.status());
    task.setModifiedBy(getLoginAccount());
    task.setModifiedDate(new Date());
    personTaskRepository.saveAndFlush(task);
  }

  @Override
  public void addDetails(String taskCode, List<PersonTaskDetail> detailList) {
    //校验任务状态
    PersonTask taskParam = new PersonTask();
    taskParam.setTaskCode(taskCode).setDeleted(false);
    PersonTask task = this.findOne(taskParam);
    Assert.state(task != null, "检测任务不存在！");
    Assert.state(TaskStatus.INIT.status().equals(task.getTaskStatus()), String.format("检测任务状态不允许添被加检测人员！[%s]", task.getTaskStatus()));
    //保存检测人员
    detailList.forEach(operator -> operator.setTaskCode(taskCode));
    personTaskDetailService.saveBatch(detailList);
    //更改任务状态为待下发
    task.setTaskStatus(TaskStatus.ASSIGNING.status());
    task.setModifiedBy(getLoginAccount());
    task.setModifiedDate(new Date());
    personTaskRepository.saveAndFlush(task);
  }
}

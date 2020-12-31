package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;
import com.alsace.exchange.service.detection.emums.TaskStatus;
import com.alsace.exchange.service.detection.repositories.PersonTaskRepository;
import com.alsace.exchange.service.detection.service.PersonTaskLocationService;
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
    Assert.notNull(task,"任务信息为空！");
    Assert.notEmpty(orgList,"检测机构为空！");
    String taskCode = orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.PERSON_TASK_CODE);
    //保存任务对应检测机构
    orgList.forEach(org->org.setTaskCode(taskCode));
    personTaskOrgService.saveBatch(orgList);
    //保存任务对应地点
    if(!CollectionUtils.isEmpty(locationList)){
      locationList.forEach(location->location.setTaskCode(taskCode));
      personTaskLocationService.saveBatch(locationList);
    }
    //保存任务
    task.setTaskCode(taskCode);
    task.setTaskStatus(TaskStatus.INIT.status());
    return this.save(task);
  }
}

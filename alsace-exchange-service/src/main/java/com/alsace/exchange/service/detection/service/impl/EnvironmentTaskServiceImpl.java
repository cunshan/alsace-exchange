package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.base.AlsaceOrderBy;
import com.alsace.exchange.common.base.AlsacePageHelper;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.enums.OrderByEnum;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.AlsaceBeanUtils;
import com.alsace.exchange.common.utils.JpaHelper;
import com.alsace.exchange.service.detection.domain.EnvironmentTask;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailResult;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskForm;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOperator;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskOrg;
import com.alsace.exchange.service.detection.domain.PersonTaskApp;
import com.alsace.exchange.service.detection.emums.EnvironmentTaskDetailResultType;
import com.alsace.exchange.service.detection.emums.TaskDetailResultStatus;
import com.alsace.exchange.service.detection.emums.TaskDetailStatus;
import com.alsace.exchange.service.detection.emums.TaskFormStatus;
import com.alsace.exchange.service.detection.emums.TaskStatus;
import com.alsace.exchange.service.detection.mapper.EnvironmentTaskMapper;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskRepository;
import com.alsace.exchange.service.detection.service.EnvironmentTaskDetailService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskFormService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskOperatorService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskOrgService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskService;
import com.alsace.exchange.service.detection.service.EnvironmentTaskTagService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EnvironmentTaskServiceImpl extends AbstractBaseServiceImpl<EnvironmentTask> implements EnvironmentTaskService {

  @Resource
  private EnvironmentTaskRepository environmentTaskRepository;
  @Resource
  private EnvironmentTaskMapper environmentTaskMapper;
  @Resource
  private EnvironmentTaskOrgService environmentTaskOrgService;
  @Resource
  private EnvironmentTaskTagService environmentTaskTagService;
  @Resource
  private EnvironmentTaskOperatorService environmentTaskOperatorService;
  @Resource
  private EnvironmentTaskDetailService environmentTaskDetailService;
  @Resource
  private EnvironmentTaskFormService environmentTaskFormService;

  @Resource
  private OrderNoGenerator orderNoGenerator;

  @Override
  protected JpaRepository<EnvironmentTask, Long> getJpaRepository() {
    return this.environmentTaskRepository;
  }

  @Override
  protected JpaSpecificationExecutor<EnvironmentTask> getJpaSpecificationExecutor() {
    return this.environmentTaskRepository;
  }

  @Override
  public PageResult<EnvironmentTask> findPage(PageParam<EnvironmentTask> param) {
    Assert.notNull(param.getParam(),"参数对象为空！");
    Set<String> likeSet = new HashSet<>();
    likeSet.add("taskName");
    likeSet.add("taskDesc");
    Specification<EnvironmentTask> specs = JpaHelper.buildConditions(param.getParam(),likeSet,new AlsaceOrderBy(OrderByEnum.DESC, Arrays.asList("createdDate","modifiedDate")));
    param.getParam().setDeleted(false);
    Page<EnvironmentTask> userPage = getJpaSpecificationExecutor().findAll(specs, AlsacePageHelper.page(param));
    return new PageResult<>(userPage);


  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  @AutoFill(AutoFillType.CREATE)
  public EnvironmentTask save(EnvironmentTask task, List<EnvironmentTaskOrg> orgList) {
    Assert.notNull(task, "任务信息为空！");
    Assert.notEmpty(orgList, "检测机构为空！");
    String taskCode = orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.ENVIRONMENT_TASK_CODE);
    //保存任务对应检测机构
    orgList.forEach(org -> org.setTaskCode(taskCode));
    environmentTaskOrgService.saveBatch(orgList);
    //保存任务
    task.setTaskCode(taskCode);
    task.setTaskStatus(TaskStatus.INIT.status());
    return save(task);
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  @AutoFill(AutoFillType.UPDATE)
  public EnvironmentTask update(EnvironmentTask task, List<EnvironmentTaskOrg> orgList) {
    Assert.hasLength(task.getTaskCode(), "任务编码为空！");
    Assert.notEmpty(orgList, "检测机构为空！");
    EnvironmentTask queryTask = new EnvironmentTask();
    queryTask.setTaskCode(task.getTaskCode())
        .setDeleted(false);
    EnvironmentTask dbTask = this.environmentTaskRepository.findOne(Example.of(queryTask)).orElseThrow(() -> new AlsaceException("任务不存在！"));
    //校验任务状态如果已经分配了被检测人员 不能修改
    if (!TaskStatus.INIT.status().equals(dbTask.getTaskStatus())) {
      throw new AlsaceException("当前任务状态不允许修改！");
    }
    AlsaceBeanUtils.copyNotNullProperties(task, dbTask);
    this.environmentTaskRepository.saveAndFlush(dbTask);
    //删除检测机构和检测地点
    environmentTaskOrgService.deleteByTaskCode(task.getTaskCode());
    environmentTaskTagService.deleteByTaskCode(task.getTaskCode());
    //保存检测机构
    orgList.forEach(org -> {
      org.setTaskCode(task.getTaskCode());
      setCreateInfo(org);
    });
    environmentTaskOrgService.saveBatch(orgList);
    return task;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void addOperators(String taskCode, List<EnvironmentTaskOperator> operatorList) {
    //校验任务状态
    EnvironmentTask taskParam = new EnvironmentTask();
    taskParam.setTaskCode(taskCode).setDeleted(false);
    EnvironmentTask task = this.findOne(taskParam);
    Assert.state(task != null, "检测任务不存在！");
//    Assert.state(TaskStatus.ASSIGNED.status().equals(task.getTaskStatus()), String.format("检测任务状态不允许添加检测人员！[%s]", task.getTaskStatus()));
    //保存检测人员
    operatorList.forEach(operator -> operator.setTaskCode(taskCode));
    environmentTaskOperatorService.saveBatch(operatorList);
    //更改任务状态为待下发
    if (TaskStatus.READY.status() > task.getTaskStatus()) {
      task.setTaskStatus(TaskStatus.READY.status());
    }
    task.setModifiedBy(getLoginAccount());
    task.setModifiedDate(new Date());
    environmentTaskRepository.saveAndFlush(task);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addDetails(String taskCode, List<EnvironmentTaskDetail> detailList) {
    //校验任务状态
    EnvironmentTask taskParam = new EnvironmentTask();
    taskParam.setTaskCode(taskCode).setDeleted(false);
    EnvironmentTask task = this.findOne(taskParam);
    Assert.state(task != null, "检测任务不存在！");
    Assert.state(TaskStatus.COMPLETED.status() > task.getTaskStatus(), String.format("检测任务状态不允许添被加检测人员！[%s]", task.getTaskStatus()));
    //如果结束时间已经到了，不准修改
    if (task.getEndDate().getTime() <= new Date().getTime()) {
      throw new AlsaceException("任务已经结束，修改失败！");
    }
    //保存检测环境
    detailList.forEach(detail -> detail.setTaskCode(taskCode)
        .setDetailStatus(TaskDetailStatus.INIT.status())
        .setDetailCode(orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.ENVIRONMENT_TASK_DETAIL_CODE)));
    environmentTaskDetailService.saveBatch(detailList);
    //更改任务状态为待下发
    if (TaskStatus.INIT.status().equals(task.getTaskStatus())) {
      task.setTaskStatus(TaskStatus.ASSIGNING.status());
      task.setModifiedBy(getLoginAccount());
      task.setModifiedDate(new Date());
      environmentTaskRepository.saveAndFlush(task);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public EnvironmentTaskForm startTask(String taskCode) {
    Assert.hasLength(taskCode, "任务编码为空！");
    EnvironmentTask taskParam = new EnvironmentTask();
    taskParam.setTaskCode(taskCode).setDeleted(false);
    EnvironmentTask task = this.findOne(taskParam);
    if (!TaskStatus.READY.status().equals(task.getTaskStatus()) && !TaskStatus.PROCESSING.status().equals(task.getTaskStatus())) {
      throw new AlsaceException(String.format("任务状态为%s，不能开始任务！", TaskStatus.getDesc(task.getTaskStatus())));
    }
    if (TaskStatus.READY.status().equals(task.getTaskStatus())) {
      //如果是待开始的  修改任务状态
      task.setTaskStatus(TaskStatus.PROCESSING.status());
      this.environmentTaskRepository.saveAndFlush(task);
    }
    //是否有开始的表单 如果没有 创建并返回
    EnvironmentTaskForm formParam = new EnvironmentTaskForm();
    formParam.setTaskCode(taskCode)
        .setFormStatus(TaskFormStatus.PROCESSING.status())
        .setCreatedBy(loginInfoProvider.loginAccount()).setDeleted(false);
    EnvironmentTaskForm form = environmentTaskFormService.findOne(formParam);
    if (form == null) {
      //如果当前时间超过任务结束时间时，不可再次创建新表单
      Assert.state(task.getEndDate().getTime() > new Date().getTime(), "当前任务已到达结束时间，不可创建新表单！");
      formParam.setFormCode(orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.ENVIRONMENT_TASK_FORM_CODE));
      return environmentTaskFormService.save(formParam);
    }
    return form;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void submitDetail(EnvironmentTaskDetail param) {
    Assert.hasLength(param.getTaskCode(), "任务编码为空！");
    Assert.hasLength(param.getFormCode(), "表单编码为空！");
    Assert.hasLength(param.getDetailCode(), "明细编码为空！");
    Assert.notNull(param.getTagId(), "标签为空！");
    Assert.notEmpty(param.getDetailResultList(), "检测样本为空！");
    //查询出任务
    EnvironmentTask queryTask = new EnvironmentTask();
    queryTask.setTaskCode(param.getTaskCode())
        .setDeleted(false);
    EnvironmentTask task = this.findOne(queryTask);
    Assert.state(task != null, String.format("任务不存在！[%s]", param.getTaskCode()));
    //查询出任务明细
    EnvironmentTaskDetail queryDetail = new EnvironmentTaskDetail();
    queryDetail.setDetailCode(param.getDetailCode()).setDeleted(false);
    EnvironmentTaskDetail dbDetail = this.environmentTaskDetailService.findOne(queryDetail);
    Assert.state(dbDetail != null, String.format("任务明细不存在！[%s]", param.getDetailCode()));
    Assert.state(TaskDetailStatus.INIT.status().equals(dbDetail.getDetailStatus()), String.format("明细已经提交！[%s]", dbDetail.getDetailCode()));
    //任务明细改成已提交
    dbDetail.setDetailStatus(TaskDetailStatus.SUBMITTED.status());
    this.environmentTaskDetailService.save(dbDetail);
    List<EnvironmentTaskDetailResult> resultList = param.getDetailResultList();
    // 提交检测明细样本要在3条之内
    Assert.state(resultList.size() > 3, "检测样本数量超过3个！");
    resultList.forEach(result -> {
      if (EnvironmentTaskDetailResultType.FOOD.status().equals(result.getDetailType())) {
        //食物的要求3个字段必填
        Assert.hasLength(result.getManufacturePlace(), "产地为空！");
        Assert.hasLength(result.getLotNumber(), "批号为空！");
        Assert.notNull(result.getManufactureDate(), "生产日期为空！");
      }
    });
    resultList.forEach(result ->
        result.setDetailCode(dbDetail.getDetailCode())
            .setTaskCode(dbDetail.getTaskCode())
            .setResultStatus(TaskDetailResultStatus.INIT.status()));
    this.environmentTaskDetailService.saveResult(resultList);
  }

  @Override
  public PageResult<EnvironmentTask> findEnvironmentTaskApp(PageParam<EnvironmentTask> param) {
    String loginAccount = getLoginAccount();
    PageInfo<EnvironmentTask> pageInfo = PageHelper.startPage(param.getPageNum(), param.getPageSize())
        .doSelectPageInfo(() -> environmentTaskMapper.selectAppTaskList(loginAccount,param.getParam()));
    return new PageResult<>(pageInfo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assign(@Validated @NotEmpty(message = "任务编码列表为空！") List<String> taskCodeList) {
    List<EnvironmentTask> taskList = environmentTaskRepository.findAll(
        (root, query, builder) -> query.where(builder.in(root.get("taskCode")).value(taskCodeList), builder.equal(root.get("deleted"), false)).getRestriction());
    taskList.forEach(task -> {
      if (!TaskStatus.ASSIGNING.status().equals(task.getTaskStatus())) {
        throw new AlsaceException(String.format("任务%s不是待下发状态！", task.getTaskCode()));
      }
      task.setTaskStatus(TaskStatus.ASSIGNED.status());
      setModifyInfo(task);
    });
    this.environmentTaskRepository.saveAll(taskList);
  }

}

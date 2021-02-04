package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.base.CodeName;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.AlsaceBeanUtils;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskApp;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import com.alsace.exchange.service.detection.domain.PersonTaskDetectionMethod;
import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import com.alsace.exchange.service.detection.domain.PersonTaskOperator;
import com.alsace.exchange.service.detection.domain.PersonTaskOrg;
import com.alsace.exchange.service.detection.emums.PersonTaskDetectionType;
import com.alsace.exchange.service.detection.emums.TaskDetailResultStatus;
import com.alsace.exchange.service.detection.emums.TaskDetailStatus;
import com.alsace.exchange.service.detection.emums.TaskFormStatus;
import com.alsace.exchange.service.detection.emums.TaskStatus;
import com.alsace.exchange.service.detection.mapper.PersonTaskMapper;
import com.alsace.exchange.service.detection.repositories.PersonTaskRepository;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import com.alsace.exchange.service.detection.service.PersonTaskDetectionMethodService;
import com.alsace.exchange.service.detection.service.PersonTaskFormService;
import com.alsace.exchange.service.detection.service.PersonTaskLocationService;
import com.alsace.exchange.service.detection.service.PersonTaskOperatorService;
import com.alsace.exchange.service.detection.service.PersonTaskOrgService;
import com.alsace.exchange.service.detection.service.PersonTaskService;
import com.alsace.exchange.service.sys.domain.UserData;
import com.alsace.exchange.service.sys.service.UserDataService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonTaskServiceImpl extends AbstractBaseServiceImpl<PersonTask> implements PersonTaskService {

  @Resource
  private PersonTaskRepository personTaskRepository;
  @Resource
  private PersonTaskMapper personTaskMapper;
  @Resource
  private PersonTaskOrgService personTaskOrgService;
  @Resource
  private PersonTaskLocationService personTaskLocationService;
  @Resource
  private PersonTaskOperatorService personTaskOperatorService;
  @Resource
  private PersonTaskDetailService personTaskDetailService;
  @Resource
  private PersonTaskFormService personTaskFormService;
  @Resource
  private PersonTaskDetectionMethodService personTaskDetectionMethodService;
  @Resource
  private UserDataService userDataService;


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
  @AutoFill(AutoFillType.CREATE)
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
    }
    locationList.add(new PersonTaskLocation().setLocationName("无地点要求"));
    locationList.forEach(location -> location.setTaskCode(taskCode));
    personTaskLocationService.saveBatch(locationList);
    //保存任务对应检测项目
    List<PersonTaskDetectionMethod> methodList = task.getMethodList();
    Assert.notEmpty(methodList, "检测项目为空！");
    methodList.forEach(method -> method.setTaskCode(taskCode));
    personTaskDetectionMethodService.saveBatch(methodList);
    //保存任务
    task.setTaskCode(taskCode);
    task.setTaskStatus(TaskStatus.INIT.status());
    return save(task);
  }

  @Override
  @Transactional(readOnly = true)
  public PageResult<PersonTask> findPage(PageParam<PersonTask> param) {
    PersonTask taskParam = param.getParam();
    String loginAccount = getLoginAccount();
    taskParam.setUserDataAccount(loginAccount);
    //查询出当前人的数据权限
    UserData queryUserData = new UserData();
    queryUserData.setLoginAccount(loginAccount).setDeleted(false);
    List<UserData> userDataList = userDataService.findAll(queryUserData);
    List<CodeName> codeNames = new ArrayList<>(userDataList.size());
    userDataList.forEach(userData -> codeNames.add(new CodeName(userData.getDataCode(), userData.getDataLabel())));
    taskParam.setUserDataList(codeNames);
    PageInfo<PersonTask> pageInfo =
        PageHelper.startPage(param.getPageNum(), param.getPageSize())
            .doSelectPageInfo(() -> personTaskMapper.findPage(taskParam));
    return new PageResult<>(pageInfo);
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  @AutoFill(AutoFillType.UPDATE)
  public PersonTask update(PersonTask task, List<PersonTaskOrg> orgList, List<PersonTaskLocation> locationList) {
    Assert.hasLength(task.getTaskCode(), "任务编码为空！");
    Assert.notEmpty(orgList, "检测机构为空！");
    PersonTask queryTask = new PersonTask();
    queryTask.setTaskCode(task.getTaskCode())
        .setDeleted(false);
    PersonTask dbTask = this.personTaskRepository.findOne(Example.of(queryTask)).orElseThrow(() -> new AlsaceException("任务不存在！"));
    //校验任务状态如果已经分配了被检测人员 不能修改
    if (!TaskStatus.INIT.status().equals(dbTask.getTaskStatus())) {
      throw new AlsaceException("当前任务状态不允许修改！");
    }
    AlsaceBeanUtils.copyNotNullProperties(task, dbTask);
    this.personTaskRepository.saveAndFlush(dbTask);
    //删除检测机构和检测地点
    personTaskOrgService.deleteByTaskCode(task.getTaskCode());
    personTaskLocationService.deleteByTaskCode(task.getTaskCode());
    //保存检测机构
    orgList.forEach(org -> {
      org.setTaskCode(task.getTaskCode());
      setCreateInfo(org);
    });
    personTaskOrgService.saveBatch(orgList);
    //保存检测地点
    locationList.forEach(location -> {
      location.setTaskCode(task.getTaskCode());
      setCreateInfo(location);
    });
    personTaskLocationService.saveBatch(locationList);
    return task;
  }

  @Override
  @Transactional(rollbackFor = {Exception.class})
  public void addOperators(String taskCode, List<PersonTaskOperator> operatorList) {
    //校验任务状态
    PersonTask taskParam = new PersonTask();
    taskParam.setTaskCode(taskCode).setDeleted(false);
    PersonTask task = this.findOne(taskParam);
    Assert.state(task != null, "检测任务不存在！");
//    Assert.state(TaskStatus.ASSIGNED.status().equals(task.getTaskStatus()), String.format("检测任务状态不允许添加检测人员！[%s]", task.getTaskStatus()));
    //保存检测人员
    operatorList.forEach(operator -> operator.setTaskCode(taskCode));
    personTaskOperatorService.saveBatch(operatorList);
    //更改任务状态为待下发
    if (TaskStatus.READY.status() > task.getTaskStatus()) {
      task.setTaskStatus(TaskStatus.READY.status());
    }
    task.setModifiedBy(getLoginAccount());
    task.setModifiedDate(new Date());
    personTaskRepository.saveAndFlush(task);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void addDetails(String taskCode, List<PersonTaskDetail> detailList) {
    //校验任务状态
    PersonTask taskParam = new PersonTask();
    taskParam.setTaskCode(taskCode).setDeleted(false);
    PersonTask task = this.findOne(taskParam);
    Assert.state(task != null, "检测任务不存在！");
    Assert.state(PersonTaskDetectionType.NOT_ALL.status().equals(task.getDetectionType()), "检测任务类型为全民检测，不能添加被检测人员！");
    Assert.state(TaskStatus.COMPLETED.status() > task.getTaskStatus(), String.format("检测任务状态不允许添被加检测人员！[%s]", task.getTaskStatus()));
    //如果结束时间已经到了，不准修改
    if (task.getEndDate().getTime() <= new Date().getTime()) {
      throw new AlsaceException("任务已经结束，修改失败！");
    }
    //保存检测人员
    detailList.forEach(detail -> detail.setTaskCode(taskCode)
        .setDetailStatus(TaskDetailStatus.INIT.status())
        .setDetailCode(orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.PERSON_TASK_DETAIL_CODE)));
    personTaskDetailService.saveBatch(detailList);
    //更改任务状态为待下发
    if (TaskStatus.INIT.status().equals(task.getTaskStatus())) {
      task.setTaskStatus(TaskStatus.ASSIGNING.status());
      task.setModifiedBy(getLoginAccount());
      task.setModifiedDate(new Date());
      personTaskRepository.saveAndFlush(task);
    }
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public PersonTaskForm startTask(String taskCode) {
    Assert.hasLength(taskCode, "任务编码为空！");
    PersonTask taskParam = new PersonTask();
    taskParam.setTaskCode(taskCode).setDeleted(false);
    PersonTask task = this.findOne(taskParam);
    if (!TaskStatus.READY.status().equals(task.getTaskStatus()) && !TaskStatus.PROCESSING.status().equals(task.getTaskStatus())) {
      throw new AlsaceException(String.format("任务状态为%s，不能开始任务！", TaskStatus.getDesc(task.getTaskStatus())));
    }
    if (TaskStatus.READY.status().equals(task.getTaskStatus())) {
      //如果是待开始的  修改任务状态
      task.setTaskStatus(TaskStatus.PROCESSING.status());
      this.personTaskRepository.saveAndFlush(task);
    }
    //是否有开始的表单 如果没有 创建并返回
    PersonTaskForm formParam = new PersonTaskForm();
    formParam.setTaskCode(taskCode)
        .setFormStatus(TaskFormStatus.PROCESSING.status())
        .setCreatedBy(loginInfoProvider.loginAccount()).setDeleted(false);
    PersonTaskForm form = personTaskFormService.findOne(formParam);
    if (form == null) {
      //如果当前时间超过任务结束时间时，不可再次创建新表单
      Assert.state(task.getEndDate().getTime() > new Date().getTime(), "当前任务已到达结束时间，不可创建新表单！");
      formParam.setFormCode(orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.PERSON_TASK_FORM_CODE));
      return personTaskFormService.save(formParam);
    }
    return form;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void submitDetail(PersonTaskDetail param) {
    Assert.hasLength(param.getTaskCode(), "任务编码为空！");
    Assert.hasLength(param.getFormCode(), "表单编码为空！");
    Assert.notNull(param.getLocationId(), "地点为空！");
    Assert.notEmpty(param.getDetailResultList(), "试管明细为空！");
    //查询出任务
    PersonTask queryTask = new PersonTask();
    queryTask.setTaskCode(param.getTaskCode())
        .setDeleted(false);
    PersonTask task = this.findOne(queryTask);
    if (task == null) {
      throw new AlsaceException("任务不存在！");
    }
    PersonTaskLocation queryLocation = new PersonTaskLocation();
    queryLocation.setTaskCode(param.getTaskCode()).setId(param.getLocationId()).setDeleted(false);
    PersonTaskLocation dbLocation = personTaskLocationService.findOne(queryLocation);
    Assert.state(dbLocation != null, "地点不存在！");
    boolean checkDetail = PersonTaskDetectionType.NOT_ALL.status().equals(task.getDetectionType());
    if (checkDetail) {
      //非全民检测
      saveNotAll(param);
    } else {
      //全民检测
      saveAll(param);
    }
  }

  private void saveAll(PersonTaskDetail param) {
    //按照身份证校验重复
    PersonTaskDetail detailQuery = new PersonTaskDetail();
    detailQuery.setTaskCode(param.getTaskCode()).setIdCardNo(param.getIdCardNo()).setDeleted(true);
    PersonTaskDetail dbDetail = personTaskDetailService.findOne(detailQuery);
    Assert.notNull(dbDetail, "该身份证对应检测明细已经存在！");
    //全民检测 直接添加检测明细
    param.setDetailCode(orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.PERSON_TASK_DETAIL_CODE));
    param.setDetailStatus(TaskDetailStatus.SUBMITTED.status());
    personTaskDetailService.save(param);
    //添加试管信息
    List<PersonTaskDetailResult> resultList = param.getDetailResultList();
    resultList.forEach(result ->
        result.setDetailCode(param.getDetailCode())
            .setTaskCode(param.getTaskCode())
            .setResultStatus(TaskDetailResultStatus.INIT.status()));
    personTaskDetailService.saveResult(resultList);
  }

  /**
   * 保存非全民检测
   */
  private void saveNotAll(PersonTaskDetail param) {
    //校验提交的被检测人信息是否是在检测范围内
    PersonTaskDetail detailQuery = new PersonTaskDetail();
    detailQuery.setTaskCode(param.getTaskCode()).setIdCardNo(param.getIdCardNo()).setDeleted(false);
    PersonTaskDetail dbDetail = personTaskDetailService.findOne(detailQuery);
    if (dbDetail == null) {
      throw new AlsaceException(String.format("身份证号：%s 不在当前任务检测范围之内！", param.getIdCardNo()));
    }
    if (TaskDetailStatus.SUBMITTED.status().equals(dbDetail.getDetailStatus())) {
      throw new AlsaceException("当前检测信息已提交！");
    }
    //更新明细
    param.setId(dbDetail.getId());
    param.setDetailStatus(TaskDetailStatus.SUBMITTED.status());
    personTaskDetailService.update(param);
    //添加试管信息
    List<PersonTaskDetailResult> resultList = param.getDetailResultList();
    resultList.forEach(result ->
        result.setDetailCode(dbDetail.getDetailCode())
            .setTaskCode(dbDetail.getTaskCode())
            .setResultStatus(TaskDetailResultStatus.INIT.status()));
    personTaskDetailService.saveResult(resultList);
  }

  @Override
  public PageResult<PersonTaskApp> findPersonTaskApp(PageParam<PersonTaskApp> param) {
    String loginAccount = getLoginAccount();
    PageInfo<PersonTaskApp> pageInfo = PageHelper.startPage(param.getPageNum(), param.getPageSize())
        .doSelectPageInfo(() -> personTaskMapper.selectAppTaskList(loginAccount,param.getParam()));
    return new PageResult<>(pageInfo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void assign(@Validated @NotEmpty(message = "任务编码列表为空！") List<String> taskCodeList) {
    List<PersonTask> taskList = personTaskRepository.findAll(
        (root, query, builder) -> query.where(builder.in(root.get("taskCode")).value(taskCodeList), builder.equal(root.get("deleted"), false)).getRestriction());
    taskList.forEach(task -> {
      if (!TaskStatus.ASSIGNING.status().equals(task.getTaskStatus())) {
        throw new AlsaceException(String.format("任务%s不是待下发状态！", task.getTaskCode()));
      }
      task.setTaskStatus(TaskStatus.ASSIGNED.status());
      setModifyInfo(task);
    });
    this.personTaskRepository.saveAll(taskList);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateResultBatch(List<String> taskCodeList, Boolean positive) {
    //校验任务状态必须为进行中的
    taskCodeList.forEach(taskCode -> {
      PersonTask queryTask = new PersonTask();
      queryTask.setTaskCode(taskCode).setDeleted(false);
      PersonTask task = this.findOne(queryTask);
      Assert.state(TaskStatus.PROCESSING.status().equals(task.getTaskStatus()), String.format("任务【%s】不是进行中的任务，不能提交检测结果！", taskCode));
      task.setTaskStatus(TaskStatus.COMPLETED.status());
      setModifyInfo(task);
      personTaskRepository.saveAndFlush(task);
    });
    //更新所有没有提交的检测结果
    personTaskDetailService.updateResultBatch(taskCodeList,positive);

  }

}

package com.alsace.exchange.service.detection.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailImport;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailResult;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskTag;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.alsace.exchange.service.detection.emums.TaskDetailStatus;
import com.alsace.exchange.service.detection.excel.EnvironmentTaskDetailVerifyService;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskDetailRepository;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskDetailResultRepository;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskTagRepository;
import com.alsace.exchange.service.detection.service.EnvironmentTaskDetailService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnvironmentTaskDetailServiceImpl extends AbstractBaseServiceImpl<EnvironmentTaskDetail> implements EnvironmentTaskDetailService {

  @Resource
  private EnvironmentTaskDetailRepository environmentTaskDetailRepository;
  @Resource
  private EnvironmentTaskDetailResultRepository environmentTaskDetailResultRepository;
  @Resource
  private EnvironmentTaskDetailVerifyService environmentTaskDetailVerifyService;
  @Resource
  private OrderNoGenerator orderNoGenerator;
  @Resource
  private EnvironmentTaskTagRepository environmentTaskTagRepository;

  @Override
  protected JpaRepository<EnvironmentTaskDetail, Long> getJpaRepository() {
    return this.environmentTaskDetailRepository;
  }

  @Override
  protected JpaSpecificationExecutor<EnvironmentTaskDetail> getJpaSpecificationExecutor() {
    return this.environmentTaskDetailRepository;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  @AutoFill(AutoFillType.UPDATE)
  public EnvironmentTaskDetail update(EnvironmentTaskDetail param) {
    Assert.notNull(param.getId(), Constants.ID_NOT_NULL_ERROR);
    EnvironmentTaskDetail dbDetail = this.getOneById(param.getId(), false);
    Assert.notNull(dbDetail, "对应明细不存在！");
    Assert.state(TaskDetailStatus.INIT.status().equals(dbDetail.getDetailStatus()), "对应明细状态已提交，不能修改！");
    return getJpaRepository().saveAndFlush(dbDetail);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean delete(List<Long> idList) {
    Assert.notEmpty(idList, "ID列表为空！");
    List<EnvironmentTaskDetail> domainList = new ArrayList<>(idList.size());
    idList.forEach(id -> {
      EnvironmentTaskDetail detail = this.getOneById(id);
      Assert.notNull(detail, "对应明细不存在！");
      Assert.state(TaskDetailStatus.INIT.status().equals(detail.getDetailStatus()), "对应明细状态已提交，不能修改！");
      detail.setDeleted(true);
      domainList.add(detail);
    });
    getJpaRepository().saveAll(domainList);
    return true;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteResultByDetailCode(String detailCode) {
    this.environmentTaskDetailResultRepository.deleteByDetailCode(detailCode);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  @AutoFill(AutoFillType.CREATE)
  public void saveResult(List<EnvironmentTaskDetailResult> resultList) {
    this.environmentTaskDetailResultRepository.saveAll(resultList);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<EnvironmentTaskDetail> importDetails(List<Object> param, String taskCode) {
    InputStream is = new ByteArrayInputStream((byte[]) param.get(0));
    ImportParams params = new ImportParams();
    params.setTitleRows(1);
    // 开启Excel校验
    params.setNeedVerfiy(true);
    params.setVerifyHandler(environmentTaskDetailVerifyService);
    try {
      ExcelImportResult<EnvironmentTaskDetailImport> importResult = ExcelImportUtil.importExcelMore(is, EnvironmentTaskDetailImport.class, params);
      if (importResult.isVerfiyFail()) {
        StringBuffer sb = new StringBuffer();
        for (EnvironmentTaskDetailImport entity : importResult.getFailList()) {
          sb.append(String.format("第%s行的错误是:%s", entity.getRowNum(), entity.getErrorMsg()));
        }
        throw new AlsaceException(sb.toString());
      }
      //查询出任务下所有的标签
      EnvironmentTaskTag queryTag = new EnvironmentTaskTag();
      queryTag.setTaskCode(taskCode).setDeleted(false);
      List<EnvironmentTaskTag> taskTags = environmentTaskTagRepository.findAll(Example.of(queryTag));
      Map<String,EnvironmentTaskTag> tagMap = new HashMap<>();
      taskTags.forEach(tag->tagMap.put(tag.getLocationName(),tag));
      List<EnvironmentTaskDetail> environmentTaskDetails = new ArrayList<>();
      importResult.getList().forEach(environmentTaskDetailImport -> {
        EnvironmentTaskDetail environmentTaskDetail = new EnvironmentTaskDetail();
        BeanUtils.copyProperties(environmentTaskDetailImport, environmentTaskDetail);
        environmentTaskDetail.setId(IdUtils.id());
        environmentTaskDetail.setTaskCode(taskCode);
        String detailCode = orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.ENVIRONMENT_TASK_DETAIL_CODE);
        environmentTaskDetail.setDetailCode(detailCode);
        environmentTaskDetail.setCreatedBy(loginInfoProvider.loginAccount());
        environmentTaskDetail.setCreatedDate(new Date());
        environmentTaskDetail.setDeleted(false);
        environmentTaskDetail.setDetailStatus(TaskDetailStatus.INIT.status());
        //设置标签
        if(!tagMap.containsKey(environmentTaskDetailImport.getTagName())){
          //如果在任务下不存在，保存并放到map里
          EnvironmentTaskTag tag = new EnvironmentTaskTag();
          tag.setTaskCode(taskCode).setLocationName(environmentTaskDetailImport.getTagName());
          setCreateInfo(tag);
          environmentTaskTagRepository.saveAndFlush(tag);
          environmentTaskDetail.setTagId(tag.getId());
        }else {
          environmentTaskDetail.setTagId(tagMap.get(environmentTaskDetailImport.getTagName()).getId());
        }
        environmentTaskDetails.add(environmentTaskDetail);
      });
      return environmentTaskDetailRepository.saveAll(environmentTaskDetails);
    } catch (Exception e) {
      throw new AlsaceException("导入被检测环境数据异常！" + e.getMessage());
    }
  }
}

package com.alsace.exchange.service.detection.service.impl;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.constants.Constants;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailResult;
import com.alsace.exchange.service.detection.emums.TaskDetailStatus;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskDetailRepository;
import com.alsace.exchange.service.detection.repositories.EnvironmentTaskDetailResultRepository;
import com.alsace.exchange.service.detection.service.EnvironmentTaskDetailService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnvironmentTaskDetailServiceImpl extends AbstractBaseServiceImpl<EnvironmentTaskDetail> implements EnvironmentTaskDetailService {

  @Resource
  private EnvironmentTaskDetailRepository environmentTaskDetailRepository;
  @Resource
  private EnvironmentTaskDetailResultRepository environmentTaskDetailResultRepository;

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
}

package com.alsace.exchange.service.detection.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.service.detection.domain.DetectionOrg;
import com.alsace.exchange.service.detection.domain.DetectionOrgImport;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.alsace.exchange.service.detection.emums.TaskDetailStatus;
import com.alsace.exchange.service.detection.excel.DetectionOrgVerifyService;
import com.alsace.exchange.service.detection.repositories.DetectionOrgRepository;
import com.alsace.exchange.service.detection.service.DetectionOrgService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DetectionOrgServiceImpl extends AbstractBaseServiceImpl<DetectionOrg> implements DetectionOrgService {

  @Resource
  private DetectionOrgRepository detectionOrgRepository;
  @Resource
  private DetectionOrgVerifyService detectionOrgVerifyService;


  @Override
  protected JpaRepository<DetectionOrg, Long> getJpaRepository() {
    return this.detectionOrgRepository;
  }

  @Override
  protected JpaSpecificationExecutor<DetectionOrg> getJpaSpecificationExecutor() {
    return this.detectionOrgRepository;
  }

  @Override
  public List<DetectionOrg> importOrg(List<Object> param, String orgCode,String orgName) {
    InputStream is = new ByteArrayInputStream((byte[]) param.get(0));
    ImportParams params = new ImportParams();
    params.setTitleRows(1);
    // 开启Excel校验
    params.setNeedVerfiy(true);
    params.setVerifyHandler(detectionOrgVerifyService);
    try {
      ExcelImportResult<DetectionOrgImport> importResult = ExcelImportUtil.importExcelMore(is, DetectionOrgImport.class, params);
      if (importResult.isVerfiyFail()) {
        StringBuffer sb = new StringBuffer();
        for (DetectionOrgImport entity : importResult.getFailList()) {
          sb.append(String.format("第%s行的错误是:%s", entity.getRowNum(), entity.getErrorMsg()));
        }
        throw new AlsaceException(sb.toString());
      }
      List<DetectionOrg> detectionOrgs = new ArrayList<>();
      importResult.getList().forEach(DetectionOrgImport -> {
        DetectionOrg detectionOrg = new DetectionOrg();
        BeanUtils.copyProperties(DetectionOrgImport, detectionOrg);
        detectionOrg.setId(IdUtils.id());
        detectionOrg.setParentOrgCode(orgCode);
        detectionOrg.setParentOrgName(orgName);
        detectionOrg.setCreatedBy(loginInfoProvider.loginAccount());
        detectionOrg.setCreatedDate(new Date());
        detectionOrg.setDeleted(false);
        detectionOrgs.add(detectionOrg);
      });
      return detectionOrgRepository.saveAll(detectionOrgs);
    } catch (Exception e) {
      throw new AlsaceException("导入用户数据异常！" + e.getMessage());
    }
  }
}

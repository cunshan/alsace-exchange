package com.alsace.exchange.service.detection.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alsace.exchange.common.base.AbstractBaseServiceImpl;
import com.alsace.exchange.common.base.AlsaceOrderBy;
import com.alsace.exchange.common.base.AlsacePageHelper;
import com.alsace.exchange.common.base.PageParam;
import com.alsace.exchange.common.base.PageResult;
import com.alsace.exchange.common.enums.OrderByEnum;
import com.alsace.exchange.common.exception.AlsaceException;
import com.alsace.exchange.common.utils.IdUtils;
import com.alsace.exchange.common.utils.JpaHelper;
import com.alsace.exchange.service.detection.domain.DetectionOrg;
import com.alsace.exchange.service.detection.domain.DetectionOrgImport;
import com.alsace.exchange.service.detection.excel.DetectionOrgVerifyService;
import com.alsace.exchange.service.detection.repositories.DetectionOrgRepository;
import com.alsace.exchange.service.detection.service.DetectionOrgService;
import com.alsace.exchange.service.utils.OrderNoGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DetectionOrgServiceImpl extends AbstractBaseServiceImpl<DetectionOrg> implements DetectionOrgService {

  @Resource
  private DetectionOrgRepository detectionOrgRepository;
  @Resource
  private DetectionOrgVerifyService detectionOrgVerifyService;
  @Resource
  private OrderNoGenerator orderNoGenerator;


  @Override
  protected JpaRepository<DetectionOrg, Long> getJpaRepository() {
    return this.detectionOrgRepository;
  }

  @Override
  protected JpaSpecificationExecutor<DetectionOrg> getJpaSpecificationExecutor() {
    return this.detectionOrgRepository;
  }

  @Override
  public List<DetectionOrg> importOrg(List<Object> param, String orgCode, String orgName) {
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
        //拼接父级编码
        detectionOrg.setOrgCode(orgCode+"-"+orderNoGenerator.getOrderNo(OrderNoGenerator.OrderNoType.ORG_CODE));
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

  @Override
  public PageResult<DetectionOrg> findPage(PageParam<DetectionOrg> param) {
    Set<String> likeSet = new HashSet<>();
    likeSet.add("orgName");
    likeSet.add("parentOrgName");
    likeSet.add("orgAddress");
    likeSet.add("contacts");
    likeSet.add("tel");
    Specification<DetectionOrg> spec = JpaHelper.buildConditions(param.getParam(), likeSet, new AlsaceOrderBy(OrderByEnum.ASC, Arrays.asList("orgCode","createdDate")));
    return new PageResult<>(getJpaSpecificationExecutor().findAll(spec, AlsacePageHelper.page(param)));
  }
}

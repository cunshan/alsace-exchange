package com.alsace.exchange.service.detection.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailImport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnvironmentTaskDetailVerifyService implements IExcelVerifyHandler<EnvironmentTaskDetailImport> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(EnvironmentTaskDetailImport detailImport) {
        if (StringUtils.isBlank(detailImport.getCompanyCode())) {
            return new ExcelVerifyHandlerResult(false, "企业编码不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getCompanyName())) {
            return new ExcelVerifyHandlerResult(false, "企业名称不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getCategory())) {
            return new ExcelVerifyHandlerResult(false, "行业类别不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getScope())) {
            return new ExcelVerifyHandlerResult(false, "营业范围不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getCompanyName())) {
            return new ExcelVerifyHandlerResult(false, "公司名称不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getAddress())) {
            return new ExcelVerifyHandlerResult(false, "地址不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getTel())) {
            return new ExcelVerifyHandlerResult(false, "联系方式不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getChargePerson())) {
            return new ExcelVerifyHandlerResult(false, "负责人不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getChargePersonIdCard())) {
            return new ExcelVerifyHandlerResult(false, "负责人身份证号不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getTaxCode())) {
            return new ExcelVerifyHandlerResult(false, "税号不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getOrgCode())) {
            return new ExcelVerifyHandlerResult(false, "归属所编码不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getCity())) {
            return new ExcelVerifyHandlerResult(false, "归属市不能为空！");
        }
        return new ExcelVerifyHandlerResult(true);
    }

}

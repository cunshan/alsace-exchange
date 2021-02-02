package com.alsace.exchange.service.detection.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.alsace.exchange.service.detection.service.PersonTaskDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PersonTaskDetailVerifyService implements IExcelVerifyHandler<PersonTaskDetailImport> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(PersonTaskDetailImport detailImport) {
        if (StringUtils.isBlank(detailImport.getPersonName())) {
            return new ExcelVerifyHandlerResult(false, "姓名不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getGender())) {
            return new ExcelVerifyHandlerResult(false, "性别不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getAddress())) {
            return new ExcelVerifyHandlerResult(false, "地址不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getCompanyCode())) {
            return new ExcelVerifyHandlerResult(false, "公司编码不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getCompanyName())) {
            return new ExcelVerifyHandlerResult(false, "公司名称不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getIdCardNo())) {
            return new ExcelVerifyHandlerResult(false, "身份证号不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getRegion())) {
            return new ExcelVerifyHandlerResult(false, "地区不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getJob())) {
            return new ExcelVerifyHandlerResult(false, "岗位不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getTel())) {
            return new ExcelVerifyHandlerResult(false, "电话不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getWorking())) {
            return new ExcelVerifyHandlerResult(false, "在职状态不能为空！");
        }
        if (StringUtils.isBlank(detailImport.getCity())) {
            return new ExcelVerifyHandlerResult(false, "归属市不能为空！");
        }
        return new ExcelVerifyHandlerResult(true);
    }

}

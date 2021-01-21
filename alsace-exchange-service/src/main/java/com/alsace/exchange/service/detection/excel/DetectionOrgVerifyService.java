package com.alsace.exchange.service.detection.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.alsace.exchange.service.detection.domain.DetectionOrg;
import com.alsace.exchange.service.detection.domain.DetectionOrgImport;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DetectionOrgVerifyService implements IExcelVerifyHandler<DetectionOrgImport> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(DetectionOrgImport detectionOrg) {
        if (StringUtils.isBlank(detectionOrg.getOrgCode())) {
            return new ExcelVerifyHandlerResult(false, "机构编码不能为空！");
        }
        if (StringUtils.isBlank(detectionOrg.getOrgName())) {
            return new ExcelVerifyHandlerResult(false, "机构名称不能为空！");
        }
        if (StringUtils.isBlank(detectionOrg.getOrgAddress())) {
            return new ExcelVerifyHandlerResult(false, "机构地址不能为空！");
        }
        if (StringUtils.isBlank(detectionOrg.getContacts())) {
            return new ExcelVerifyHandlerResult(false, "负责人不能为空！");
        }
        if (StringUtils.isBlank(detectionOrg.getTel())) {
            return new ExcelVerifyHandlerResult(false, "联系方式不能为空！");
        }
        return new ExcelVerifyHandlerResult(true);
    }

}

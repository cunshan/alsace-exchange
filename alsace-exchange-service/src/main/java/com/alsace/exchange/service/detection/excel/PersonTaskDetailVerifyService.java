package com.alsace.exchange.service.detection.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.alsace.exchange.service.sys.domain.UserImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonTaskDetailVerifyService implements IExcelVerifyHandler<PersonTaskDetailImport> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(PersonTaskDetailImport detailImport) {
//
        return new ExcelVerifyHandlerResult(true);
    }

}

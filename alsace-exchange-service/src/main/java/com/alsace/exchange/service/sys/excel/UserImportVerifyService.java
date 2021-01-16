package com.alsace.exchange.service.sys.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.alsace.exchange.service.sys.domain.UserImport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserImportVerifyService implements IExcelVerifyHandler<UserImport> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(UserImport userImport) {
//        FmsPayableOrderContract contract = new FmsPayableOrderContract();
//        BeanUtils.copyProperties(contractImport, contract);
//        Result<String> importcontractResult = iFmsPayableOrderContractService.checkImportContract(contract,null);
//        if (!importcontractResult.getFlag()) {
//            return new ExcelVerifyHandlerResult(false, importcontractResult.getMsg());
//        }
        return new ExcelVerifyHandlerResult(true);
    }

}

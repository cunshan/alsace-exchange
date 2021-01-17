package com.alsace.exchange.service.sys.excel;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.alsace.exchange.service.sys.domain.User;
import com.alsace.exchange.service.sys.domain.UserImport;
import com.alsace.exchange.service.sys.repositories.UserRepository;
import com.alsace.exchange.service.sys.specs.UserSpecs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
@Slf4j
public class UserImportVerifyService implements IExcelVerifyHandler<UserImport> {
    @Resource
    private UserRepository userRepository;

    @Override
    public ExcelVerifyHandlerResult verifyHandler(UserImport userImport) {
        if (StringUtils.isBlank(userImport.getUserName())) {
            return new ExcelVerifyHandlerResult(false, "姓名不能为空！");
        }
        if (StringUtils.isBlank(userImport.getTel())) {
            return new ExcelVerifyHandlerResult(false, "手机号不能为空！");
        }
//        Assert.state(StringUtils.isBlank(userImport.getUserName()),"姓名不能为空！");
//        Assert.state(StringUtils.isBlank(userImport.getTel()),"手机号不能为空！");
        Optional<User> user = userRepository.findOne(UserSpecs.tel(userImport.getTel()).and(UserSpecs.deleted(false)));
        if (user.isPresent()) {
            return new ExcelVerifyHandlerResult(false, String.format("手机号%s已经存在,用户名为%s",userImport.getTel(),userImport.getUserName()));
        }
//        Assert.state(user!=null,String.format("手机号%已经存在,用户名为%",userImport.getTel(),userImport.getUserName()));
        return new ExcelVerifyHandlerResult(true);
    }

}

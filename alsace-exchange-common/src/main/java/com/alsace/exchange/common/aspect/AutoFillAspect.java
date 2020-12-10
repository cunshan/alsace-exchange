package com.alsace.exchange.common.aspect;

import com.alsace.exchange.common.annontation.AutoFill;
import com.alsace.exchange.common.base.BaseEntity;
import com.alsace.exchange.common.base.LoginInfoProvider;
import com.alsace.exchange.common.enums.AutoFillType;
import com.alsace.exchange.common.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

  @Resource
  private LoginInfoProvider loginInfoProvider;

  @Pointcut("@annotation(com.alsace.exchange.common.annontation.AutoFill)")
  public void autoFill() {

  }

  @Around("autoFill()")
  public Object doProceed(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();
    AutoFill autoFill = signature.getMethod().getDeclaredAnnotation(AutoFill.class);
    String loginAccount = loginInfoProvider.loginAccount();
    Arrays.stream(args).forEach(arg -> {
      if (arg instanceof BaseEntity) {
        //单个参数
        fillEntity(arg, autoFill, loginAccount);
      } else if (arg instanceof List) {
        //参数是list的
        List list = (List) arg;
        if (!list.isEmpty() && list.get(0) instanceof BaseEntity) {
          for (Object obj : list) {
            fillEntity(obj, autoFill, loginAccount);
          }
        }
      }
    });
    return joinPoint.proceed();
  }

  private void fillEntity(Object arg, AutoFill autoFill, String loginAccount) {
    BaseEntity entity = (BaseEntity) arg;
    if (AutoFillType.CREATE.equals(autoFill.value())) {
      //新增的
      entity.setId(IdUtils.id());
      entity.setCreatedDate(new Date());
      entity.setCreatedBy(loginAccount);
      entity.setDeleted(false);
    } else if (AutoFillType.DELETE.equals(autoFill.value())) {
      //逻辑删除的
      entity.setDeleted(true);
    }
    //单纯修改的
    entity.setModifiedDate(new Date());
    entity.setModifiedBy(loginAccount);
  }

}

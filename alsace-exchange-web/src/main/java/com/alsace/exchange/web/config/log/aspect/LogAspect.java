package com.alsace.exchange.web.config.log.aspect;

import com.alsace.exchange.common.utils.JsonUtils;
import com.alsace.exchange.service.sys.domain.OperationLog;
import com.alsace.exchange.service.sys.enums.OperationLogType;
import com.alsace.exchange.service.utils.ApplicationContextHolder;
import com.alsace.exchange.web.config.log.annontation.Log;
import com.alsace.exchange.web.config.log.event.OperationLogEvent;
import com.alsace.exchange.web.utils.HttpContextUtil;
import com.alsace.exchange.web.utils.LoginUtils;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 日志aop
 */
@Slf4j
@Aspect
@Component
public class LogAspect {


    @Pointcut(value = "@annotation(com.alsace.exchange.web.config.log.annontation.Log)")
    public void cutService() {
    }

    @Around("cutService()")
    public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {

        long startTime = System.currentTimeMillis();
        //先执行业务
        Object result = point.proceed();
        long endTime = System.currentTimeMillis();
        try {
            handle(point, endTime - startTime, null);
        } catch (Exception e) {
            log.error("日志记录出错!", e);
        }
        return result;
    }

    @AfterThrowing(pointcut = "cutService()", throwing = "e")
    public void doAfterThrowing(JoinPoint point, Throwable e) {
        try {
            handle(point, null, e);
        } catch (Exception ex) {
            log.error("日志记录出错!", ex);
        }
    }

    private void handle(JoinPoint point, Long methodProcessTime, Throwable e) throws Exception {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        MethodSignature msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        String methodName = sig.getDeclaringTypeName() + "." + currentMethod.getName();
        //获取拦截方法的参数
        Object[] params = point.getArgs();
        //获取操作名称
        Log annotation = currentMethod.getAnnotation(Log.class);
        String moduleName = annotation.moduleName();
        String operationName = annotation.value();
        OperationLog opLog = new OperationLog();
        opLog.setModuleName(moduleName);
        opLog.setOperation(operationName);
        opLog.setMethod(methodName);
        opLog.setTime(methodProcessTime);
        opLog.setRequestUrl(HttpContextUtil.requestUrl());
        opLog.setRequestMethod(HttpContextUtil.requestMethod());
        opLog.setUserAgent(HttpContextUtil.getUserAgent());
        opLog.setIp(HttpContextUtil.getRemoteAddr());
        opLog.setParams(JsonUtils.toJson(params));
        opLog.setLogType(OperationLogType.ACCESS_LOG.name());
        opLog.setLoginAccount(LoginUtils.loginAccount());
        opLog.setUserName(LoginUtils.userName());
        if(e !=null){
            opLog.setException(Throwables.getStackTraceAsString(e));
        }
        ApplicationContextHolder.publishEvent(new OperationLogEvent(opLog));
    }

}

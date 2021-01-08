package com.alsace.exchange.common.base;

import javax.annotation.Resource;

/**
 * 基础cotroller
 */
public class BaseController {

    @Resource
    protected LoginInfoProvider loginInfoProvider;

    /**
     * 获取当前登录人账号
     */
    protected String getLoginAccount(){
        return loginInfoProvider.loginAccount();
    }

    /**
     * 获取当前登录人姓名
     */
    protected String getUserName(){
        return loginInfoProvider.userName();
    }

    /**
     * 错误结果
     */
    protected static <T> AlsaceResult<T> error(String errorCode, String msg) {
        return error(errorCode, msg, null);
    }

    /**
     * 错误结果
     */
    protected static <T> AlsaceResult<T> error(String errorCode, String msg, T data) {
        return new AlsaceResult<T>().setSuccess(false).setErrorCode(errorCode).setMsg(msg).setData(data);
    }

    /**
     * 成功结果
     */
    protected static <T> AlsaceResult<T> success(String msg, T data) {
        return new AlsaceResult<T>().setSuccess(true).setData(data).setMsg(msg);
    }


    /**
     * 成功结果
     */
    protected static <T> AlsaceResult<T> success(T data) {
        return success("",data);
    }

}

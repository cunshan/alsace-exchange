package com.alsace.exchange.common.base;

/**
 * 基础cotroller
 */
public class BaseController {


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

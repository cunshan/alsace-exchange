package com.alsace.exchange.common.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 执行结果
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class AlsaceResult<T> implements Serializable {
    private static final long serialVersionUID = 5648902801903379068L;

    /**
     * 是否成功标记
     */
    private boolean success;

    /**
     * 提示信息（包含成功信息和错误信息）
     */
    private String msg;

    /**
     * 系统错误码
     */
    private String errorCode;

    /**
     * 执行结果
     */
    private T data;

}

package com.alsace.exchange.web.utils;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Excel下拉框数据
 * @author yuchen
 */
@Target(FIELD)
@Retention(RUNTIME)
@Documented
public @interface ExcelDropDown {

    /**
     * 起始行
     *
     * @return
     */
    int firstRow() default 1;

    /**
     * 结束行
     *
     * @return
     */
    int lastRow() default 1000;

    /**
     * 起始列
     *
     * @return
     */
    int firstCol() default 1;

    /**
     * 结束列
     *
     * @return
     */
    int lastCol() default 1;

    /**
     * 数据kEY
     *
     * @return
     */
    String dataKey() default "";

    /**
     * 下拉数据类型
     * 此值未 DropDownEnum 的value字段
     *
     * @return
     */
    int dropType() default 1;

}

package com.alsace.exchange.common.annontation;

import com.alsace.exchange.common.enums.AutoFillType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解的类中如果存在save*()方法 自动填充id,创建时间，创建人，修改时间和修改人字段
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

  AutoFillType value();

}

package com.alsace.exchange.web.utils;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;

import java.util.Map;

/**
 * @Description: 模板excel模板 下拉数据
 * @author: wayne
 * @create: 2021/01/12 16:41
 */
public enum DropDownEnum {
    //todo map中的value值待确定
    YES_NO(3, ImmutableMap.of("否", Boolean.FALSE, "是", Boolean.TRUE));
    @Getter
    private Integer value;
    @Getter
    private Map map;

    DropDownEnum(Integer value, Map map) {
        this.value = value;
        this.map = map;
    }

}

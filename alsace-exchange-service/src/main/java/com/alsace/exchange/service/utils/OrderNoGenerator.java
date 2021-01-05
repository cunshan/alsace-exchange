package com.alsace.exchange.service.utils;

import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Component
public class OrderNoGenerator {

    private static final String REDIS_PREFIX = "FMS_ORDER_NO_";

    @Resource
    private RedisTemplate<String, String> redisClient;

    /**
     * 获取对应类型单号.
     */
    public String getOrderNo(OrderNoType type) {
        String dateStr = type.dateFormat.format(new Date());
        // 构造redis的key
        String key = REDIS_PREFIX + dateStr + "_" + type.prefix;
        // 判断key是否存在
        Boolean exists = redisClient.hasKey(key);
        Long incr = redisClient.opsForValue().increment(key);
        // 设置过期时间
        if (!exists) {
            // 构造redis过期时间 UnixMillis
            // 设置过期时间为当前时间的最后一秒
            long expire = this.offsetTime(type);
            redisClient.expire(key, expire, TimeUnit.MILLISECONDS);
        }
        // 默认编码需要5位，位数不够前面补0
        String formatNum = String.format("%0" + type.indexCount + "d", incr);
        // 转换成业务需要的格式  bizCode + date + incr
        return type.prefix + dateStr + formatNum;
    }

    /**
     * 获取单号过期时间
     */
    private long offsetTime(OrderNoType type) {

        switch (type.dateFormatStr) {
            case "yyMM":
                return this.offsetTimeMonth();
            case "yyMMdd":
                return this.offsetTimeToday();
            default:
                throw new RuntimeException("时间格式匹配错误");
        }


    }

    private long offsetTimeMonth() {
        // 当前月最后一天的23:59:59 9999
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        commOffsetTime(calendar);
        return calendar.getTimeInMillis();
    }

    /**
     * 当前时间 与 当前天最后一秒 时间差
     */
    private long offsetTimeToday() {
        // 当天的23:59:59 9999
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        commOffsetTime(calendar);
        return calendar.getTimeInMillis();
    }

    /**
     * 通用配置
     *
     */
    private void commOffsetTime(Calendar calendar) {
        calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }

    @Getter
    public enum OrderNoType {

        /**
         * 应收单号
         */
        PERSON_TASK_CODE("P", "人员检测任务号", 5, "yyMMdd"),
        /**
         * 检测表单编号
         */
        PERSON_TASK_FORM_CODE("F", "人员检测表单编码", 6, "yyMMdd"),
        /**
         * 检测明细编号
         */
        PERSON_TASK_DETAIL_CODE("D", "人员检测明细编码", 6, "yyMMdd");



        /**
         * 单号前缀
         */
        private final String prefix;
        /**
         * 单号名称
         */
        private final String name;
        /**
         * 单号序列号位数
         */
        private final Integer indexCount;
        /**
         * 日期formatter
         */
        private final SimpleDateFormat dateFormat;
        /**
         * 日期字符
         */
        private final String dateFormatStr;

        OrderNoType(String prefix, String name, Integer indexCount, String dateFormatStr) {
            this.prefix = prefix;
            this.name = name;
            this.indexCount = indexCount;
            this.dateFormat = new SimpleDateFormat(dateFormatStr);
            this.dateFormatStr = dateFormatStr;
        }
    }

}

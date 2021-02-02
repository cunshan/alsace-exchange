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

  private static final String REDIS_PREFIX = "ALSACE_ORDER_NO_";

  @Resource
  private RedisTemplate<String, String> redisClient;

  /**
   * 获取对应类型单号.
   */
  public String getOrderNo(OrderNoType type) {
    String dateStr = type.dateFormat.format(new Date());
    // 构造redis的key
    String key = REDIS_PREFIX + dateStr + "_" + type.prefix;
    Long incr = redisClient.opsForValue().increment(key);
    // 设置过期时间
    if (incr.intValue() == 1L) {
      //说明原来不存在  第一次保存
      // 构造redis过期时间 UnixMillis
      // 设置过期时间为当前时间的后一天避免单号被重置
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
        return 3600*24*1000L;
      default:
        throw new RuntimeException("时间格式匹配错误");
    }


  }

  private long offsetTimeMonth() {
    // 下个月1号跟当前时间的差值
    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
    calendar.add(Calendar.MONTH,1);
    calendar.set(Calendar.DAY_OF_MONTH,1);
    return calendar.getTimeInMillis() - new Date().getTime();
  }

  @Getter
  public enum OrderNoType {

    /**
     * 人员检测任务编码
     */
    PERSON_TASK_CODE("P", "人员检测任务号", 5, "yyMMdd"),
    /**
     * 人员检测表单编码
     */
    PERSON_TASK_FORM_CODE("PF", "人员检测表单编码", 6, "yyMMdd"),
    /**
     * 人员检测明细编码
     */
    PERSON_TASK_DETAIL_CODE("PD", "人员检测明细编码", 6, "yyMMdd"),
    /**
     * 环境检测任务编码
     */
    ENVIRONMENT_TASK_CODE("E", "环境检测任务号", 5, "yyMMdd"),
    /**
     * 环境检测表单编码
     */
    ENVIRONMENT_TASK_FORM_CODE("EF", "环境检测表单编码", 6, "yyMMdd"),
    /**
     * 环境检测明细编码
     */
    ENVIRONMENT_TASK_DETAIL_CODE("ED", "环境检测明细编码", 6, "yyMMdd"),

    /**
     * 机构父类编码
     */
    ORG_PARENT_CODE("G", "机构父类编码", 3, "yy"),

    /**
     * 机构子类编码
     */
    ORG_CODE("Z", "机构编码", 3, "dd");

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

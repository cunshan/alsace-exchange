package com.alsace.exchange.service.sys.domain;

import com.alsace.exchange.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_operation_log")
@ApiModel
@DynamicUpdate
@Getter
@Setter
public class OperationLog extends BaseEntity {
  private static final long serialVersionUID = 5020353257577471551L;

  /**
   * 登录账号
   */
  @Column(columnDefinition = "varchar(32) comment '登录人账号'")
  private String loginAccount;

  /**
   * 用户名
   */
  @Column(columnDefinition = "varchar(32) comment '登录人姓名'")
  private String userName;

  /**
   * 用户操作
   */
  @Column(columnDefinition = "varchar(128) comment '用户操作'")
  private String operation;

  /**
   * 日志类型
   */
  @Column(columnDefinition = "varchar(32) comment '日志类型'")
  private String logType;

  /**
   * 请求方法
   */
  @Column(columnDefinition = "varchar(255) comment '请求方法'")
  private String method;

  /**
   * 请求参数
   */
  @Column(columnDefinition = "text(32) comment '请求参数'")
  private String params;

  /**
   * 执行时长(毫秒)
   */
  @Column(columnDefinition = "bigint(20) comment '执行时长(毫秒)'")
  private Long time;

  /**
   * IP地址
   */
  @Column(columnDefinition = "varchar(32) comment 'IP地址'")
  private String ip;

  /**
   * 模块名称
   */
  @Column(columnDefinition = "varchar(255) comment '模块名称'")
  private String moduleName;

  /**
   * 请求地址
   */
  @Column(columnDefinition = "varchar(255) comment '请求地址'")
  private String requestUrl;

  /**
   * 请求方法
   */
  @Column(columnDefinition = "varchar(255) comment '请求方法'")
  private String requestMethod;

  /**
   * 浏览器信息
   */
  @Column(columnDefinition = "varchar(64) comment '浏览器信息'")
  private String userAgent;

  /**
   * 异常信息
   */
  @Column(columnDefinition = "text comment '异常信息'")
  private String exception;


}

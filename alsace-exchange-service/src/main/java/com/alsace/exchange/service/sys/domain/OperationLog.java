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
   * 访问url
   */
  @Column(columnDefinition = "varchar(255) not null comment '访问url'")
  private String url;

  /**
   * 客户端地址
   */
  @Column(columnDefinition = "varchar(255) not null comment '客户端地址'")
  private String host;

  /**
   * 参数
   */
  @Column(columnDefinition = "text not null comment '参数'")
  private String param;

  /**
   * 返回结果
   */
  @Column(columnDefinition = "text not null comment '返回结果'")
  private String result;

  /**
   * 操作人账号
   */
  @Column(columnDefinition = "varchar(32) not null comment '操作人账号'")
  private String operatorAccount;

  /**
   * 操作人名称
   */
  @Column(columnDefinition = "varchar(32) not null comment '操作人名称'")
  private String operatorName;

  /**
   * 模块
   */
  @Column(columnDefinition = "varchar(32) not null comment '模块'")
  private String module;


}

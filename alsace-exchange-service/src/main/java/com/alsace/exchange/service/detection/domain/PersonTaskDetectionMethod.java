package com.alsace.exchange.service.detection.domain;

import com.alsace.exchange.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_person_task_detection_method")
@DynamicUpdate
@DynamicInsert
public class PersonTaskDetectionMethod extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

  @Column(columnDefinition = "varchar(128) not null comment '检测项目编码'")
  private String code;

  @Column(columnDefinition = "varchar(128) not null comment '检测项目名称'")
  private String name;


}

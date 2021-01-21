package com.alsace.exchange.service.detection.domain;

import com.alsace.exchange.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_person_task_form")
@ApiModel
@DynamicUpdate
public class PersonTaskForm extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @ApiModelProperty(value = "任务编码")
  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

  @ApiModelProperty(value = "表单编码")
  @Column(columnDefinition = "varchar(64) not null comment '表单编码'")
  private String formCode;

  @ApiModelProperty(value = "表单状态")
  @Column(columnDefinition = "int(2) not null comment '表单状态 10:进行中 20:已提交'")
  private Integer formStatus;

}

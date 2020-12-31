package com.alsace.exchange.service.detection.domain;

import com.alsace.exchange.common.base.BaseEntity;
import com.alsace.exchange.common.validate.groups.Create;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_person_task_location")
@ApiModel
@DynamicUpdate
public class PersonTaskLocation extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @ApiModelProperty(value = "任务编码")
  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

  @ApiModelProperty(value = "地点编码")
  @Column(columnDefinition = "varchar(64) not null comment '地点编码'")
  @NotBlank(groups = {Create.class}, message = "地点编码为空！")
  private String locationCode;

  @ApiModelProperty(value = "地点名称")
  @Column(columnDefinition = "varchar(128) not null comment '地点名称'")
  @NotBlank(groups = {Create.class}, message = "地点名称为空！")
  private String locationName;

}

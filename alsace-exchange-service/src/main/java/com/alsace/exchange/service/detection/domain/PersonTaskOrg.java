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
@Table(name = "biz_person_task_org")
@ApiModel
@DynamicUpdate
public class PersonTaskOrg extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @ApiModelProperty(value = "任务编码")
  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

  @ApiModelProperty(value = "机构名称")
  @Column(columnDefinition = "varchar(255) not null comment '机构名称'")
  @NotBlank(groups = {Create.class}, message = "机构名称为空！")
  private String orgName;

  @ApiModelProperty(value = "机构编码")
  @Column(columnDefinition = "varchar(128) not null comment '机构编码'")
  @NotBlank(groups = {Create.class}, message = "机构编码为空！")
  private String orgCode;

}

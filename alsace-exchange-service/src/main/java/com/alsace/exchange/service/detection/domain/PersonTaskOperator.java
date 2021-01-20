package com.alsace.exchange.service.detection.domain;

import com.alsace.exchange.common.base.BaseEntity;
import com.alsace.exchange.common.validate.groups.Create;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_person_task_operator")
@ApiModel
@DynamicUpdate
public class PersonTaskOperator extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @ApiModelProperty(value = "任务编码")
  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

  @ApiModelProperty(value = "任务地点ID", required = true)
  @Column(columnDefinition = "bigint(20) comment '任务地点ID'")
  @NotNull(groups = {Create.class}, message = "任务地点为空！")
  private Long locationId;

  @ApiModelProperty(value = "登录账号", required = true)
  @Column(columnDefinition = "varchar(128) not null comment '登录账号'")
  @NotBlank(groups = {Create.class}, message = "登录账号为空！")
  private String loginAccount;

  @ApiModelProperty(value = "用户姓名", required = true)
  @Column(columnDefinition = "varchar(128) not null comment '用户姓名'")
  @NotBlank(groups = {Create.class}, message = "用户姓名为空！")
  private String userName;
}

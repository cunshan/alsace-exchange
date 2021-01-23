package com.alsace.exchange.service.detection.domain;

import com.alsace.exchange.common.base.BaseEntity;
import com.alsace.exchange.common.validate.groups.Create;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_environment_task")
@ApiModel
@DynamicUpdate
@DynamicInsert
public class EnvironmentTask extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @ApiModelProperty(value = "任务编码", required = true)
  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

  @ApiModelProperty(value = "任务名称", required = true)
  @Column(columnDefinition = "varchar(255) not null comment '任务名称'")
  @NotBlank(groups = {Create.class}, message = "任务名称为空!")
  private String taskName;

  @ApiModelProperty(value = "任务描述", required = true)
  @Column(columnDefinition = "varchar(255) not null comment '任务描述'")
  @NotBlank(groups = {Create.class}, message = "任务描述为空!")
  private String taskDesc;

  @ApiModelProperty(value = "任务状态 10：已创建 20：待下发 30：已下发 40：待开始 50：进行中 60：已完成 70：取消", required = true)
  @Column(columnDefinition = "int(2) not null comment '任务状态 10：已创建 20：待下发 30：已下发 40：待开始 50：进行中 60：已完成 70：取消'")
  @NotBlank(groups = {Create.class}, message = "任务状态!")
  private Integer taskStatus;

  @ApiModelProperty(value = "任务开始时间",required = true)
  @Column(columnDefinition = "datetime not null comment '任务开始时间'")
  @NotNull(groups = {Create.class}, message = "任务开始时间为空！")
  private Date startDate;

  @ApiModelProperty(value = "任务结束时间", required = true)
  @Column(columnDefinition = "datetime not null comment '任务结束时间'")
  @NotNull(groups = {Create.class}, message = "结束开始时间为空！")
  private Date endDate;

}

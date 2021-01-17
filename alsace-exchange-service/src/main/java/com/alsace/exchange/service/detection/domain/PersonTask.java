package com.alsace.exchange.service.detection.domain;

import com.alsace.exchange.common.base.BaseEntity;
import com.alsace.exchange.common.validate.groups.Create;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_person_task")
@ApiModel
@DynamicUpdate
@DynamicInsert
public class PersonTask extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @ApiModelProperty(value = "任务编码", required = true)
  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

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

  @ApiModelProperty(value = "检测类型 1 全民检测  2 非全民检测", required = true)
  @Column(columnDefinition = "int(2) not null comment '检测类型'")
  @NotNull(groups = {Create.class}, message = "检测类型为空！")
  private Integer detectionType;

  @ApiModelProperty(value = "检测项目", required = true)
  @Column(columnDefinition = "int(2) not null comment '检测项目 1:核酸检测 2:抗体检测'")
  @NotBlank(groups = {Create.class}, message = "检测项目为空！")
  private Integer detectionMethod;

  @ApiModelProperty(value = "混管设置", required = true)
  @Column(columnDefinition = "varchar(255) not null comment '混管设置'")
  @NotBlank(groups = {Create.class}, message = "混管设置为空！")
  private String mixedMode;

}

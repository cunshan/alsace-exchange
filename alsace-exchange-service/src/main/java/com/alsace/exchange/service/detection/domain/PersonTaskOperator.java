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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
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

  @ApiModelProperty(value = "任务地点ID")
  @Column(columnDefinition = "bigint(20) not null comment '任务地点ID'")
  @NotEmpty(groups = {Create.class}, message = "任务地点为空！")
  private Long locationId;

  @ApiModelProperty(value = "姓名")
  @Column(columnDefinition = "varchar(128) not null comment '姓名'")
  @NotEmpty(groups = {Create.class}, message = "姓名为空！")
  private String personName;

  @ApiModelProperty(value = "性别")
  @Column(columnDefinition = "int(1) comment '性别 0 男性  1女性'")
  @NotNull(groups = {Create.class}, message = "性别为空！")
  private Integer gender;

  @ApiModelProperty(value = "民族")
  @Column(columnDefinition = "varchar(128) comment '民族'")
  private String nation;

  @ApiModelProperty(value = "出生日期")
  @Column(columnDefinition = "varchar(32) comment '出生日期'")
  private String birthday;

  @ApiModelProperty(value = "住址")
  @Column(columnDefinition = "varchar(255) comment '住址'")
  private String address;

  @ApiModelProperty(value = "身份证号")
  @Column(columnDefinition = "varchar(64) comment '身份证号'")
  @NotEmpty(groups = {Create.class}, message = "身份证号为空！")
  private String idCardNo;

  @ApiModelProperty(value = "地区")
  @Column(columnDefinition = "varchar(128) comment '地区'")
  private String region;

  @ApiModelProperty(value = "岗位")
  @Column(columnDefinition = "varchar(128) comment '岗位'")
  private String job;

  @ApiModelProperty(value = "电话")
  @Column(columnDefinition = "varchar(128) comment '电话'")
  @NotEmpty(groups = {Create.class}, message = "电话为空！")
  private String tel;

  @ApiModelProperty(value = "企业名称")
  @Column(columnDefinition = "varchar(128) comment '企业名称'")
  @NotEmpty(groups = {Create.class}, message = "企业名称为空！")
  private String companyName;

  @ApiModelProperty(value = "企业编码")
  @Column(columnDefinition = "varchar(128) comment '企业编码'")
  @NotEmpty(groups = {Create.class}, message = "企业编码为空！")
  private String companyCode;

  @ApiModelProperty(value = "在职状态")
  @Column(columnDefinition = "tinyint(1) not null comment '在职状态'")
  @NotNull(groups = {Create.class}, message = "在职状态为空！")
  private Boolean working;

  @ApiModelProperty(value = "归属所编码")
  @Column(columnDefinition = "varchar(128) comment '归属所编码'")
  private String orgCode;

  @ApiModelProperty(value = "归属市")
  @Column(columnDefinition = "varchar(128) comment '归属市'")
  private String city;

}

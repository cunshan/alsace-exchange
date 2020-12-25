package com.alsace.exchange.service.base.domain;

import com.alsace.exchange.common.base.BaseEntity;
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
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "cd_detection_person")
@ApiModel
@DynamicUpdate
public class DetectionPerson extends BaseEntity {

  @ApiModelProperty(value = "姓名")
  @Column(columnDefinition = "varchar(128) not null comment '姓名'")
  private String personName;

  @ApiModelProperty(value = "性别")
  @Column(columnDefinition = "int(1) comment '性别 0 男性  1女性'")
  private Integer gender;

  @ApiModelProperty(value = "民族")
  @Column(columnDefinition = "varchar(128) comment '民族'")
  private String nation;

  @ApiModelProperty(value = "出生日期")
  @Column(columnDefinition = "varchar(32) comment '出生日期'")
  private String birthday;

  @ApiModelProperty(value = "住址")
  @Column(columnDefinition = "varchar(255) comment '出生日期'")
  private String address;

  @ApiModelProperty(value = "身份证号")
  @Column(columnDefinition = "varchar(64) comment '身份证号'")
  private String idCardNo;

  @ApiModelProperty(value = "地区")
  @Column(columnDefinition = "varchar(128) comment '地区'")
  private String region;

  @ApiModelProperty(value = "岗位")
  @Column(columnDefinition = "varchar(128) comment '岗位'")
  private String job;

  @ApiModelProperty(value = "电话")
  @Column(columnDefinition = "varchar(128) comment '电话'")
  private String tel;

  @ApiModelProperty(value = "企业名称")
  @Column(columnDefinition = "varchar(128) comment '企业名称'")
  private String companyName;

  @ApiModelProperty(value = "企业编码")
  @Column(columnDefinition = "varchar(128) comment '企业编码'")
  private String companyCode;

  @ApiModelProperty(value = "在职状态")
  @Column(columnDefinition = "tinyint(1) not null comment '在职状态'")
  private Boolean working;

  @ApiModelProperty(value = "归属所编码")
  @Column(columnDefinition = "varchar(128) comment '归属所编码'")
  private String orgCode;

  @ApiModelProperty(value = "归属市")
  @Column(columnDefinition = "varchar(128) comment '归属市'")
  private String city;

}

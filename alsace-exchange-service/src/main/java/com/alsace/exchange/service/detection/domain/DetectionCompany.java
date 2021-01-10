package com.alsace.exchange.service.detection.domain;

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

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "cd_detection_company")
@ApiModel
@DynamicUpdate
public class DetectionCompany extends BaseEntity {

  private static final long serialVersionUID = -3019109688780296431L;

  @ApiModelProperty(value = "企业编码")
  @Column(columnDefinition = "varchar(128) not null comment '企业编码'")
  private String companyCode;

  @ApiModelProperty(value = "企业名称")
  @Column(columnDefinition = "varchar(128) not null comment '企业名称'")
  private String companyName;

  @ApiModelProperty(value = "行业类别")
  @Column(columnDefinition = "varchar(32) comment '行业类别'")
  private String category;

  @ApiModelProperty(value = "营业范围")
  @Column(columnDefinition = "varchar(255) comment '营业范围'")
  private String scope;

  @ApiModelProperty(value = "地址")
  @Column(columnDefinition = "varchar(255) comment '出生日期'")
  private String address;

  @ApiModelProperty(value = "联系方式")
  @Column(columnDefinition = "varchar(128) comment '联系方式'")
  private String tel;

  @ApiModelProperty(value = "负责人")
  @Column(columnDefinition = "varchar(64) comment '负责人'")
  private String chargePerson;

  @ApiModelProperty(value = "负责人身份证号")
  @Column(columnDefinition = "varchar(128) comment '负责人身份证号'")
  private String chargePersonIdCard;

  @ApiModelProperty(value = "税号")
  @Column(columnDefinition = "varchar(128) comment '税号'")
  private String taxCode;

  @ApiModelProperty(value = "归属所编码")
  @Column(columnDefinition = "varchar(128) comment '归属所编码'")
  private String orgCode;

  @ApiModelProperty(value = "归属市")
  @Column(columnDefinition = "varchar(128) comment '归属市'")
  private String city;

}

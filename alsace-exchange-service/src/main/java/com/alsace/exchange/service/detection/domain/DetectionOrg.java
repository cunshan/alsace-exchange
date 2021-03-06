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
@Table(name = "cd_detection_org")
@ApiModel
@DynamicUpdate
public class DetectionOrg extends BaseEntity {
  private static final long serialVersionUID = 7365386415095687241L;

  @ApiModelProperty(value = "机构名称")
  @Column(columnDefinition = "varchar(255) not null comment '机构名称'")
  private String orgName;

  @ApiModelProperty(value = "机构编码")
  @Column(columnDefinition = "varchar(128) not null comment '机构编码'")
  private String orgCode;


  @ApiModelProperty(value = "父级唯一码")
  @Column(columnDefinition = "varchar(128) comment '父级唯一码'")
  private String parentOrgCode;

  @ApiModelProperty(value = "父级名称")
  @Column(columnDefinition = "varchar(128) comment '父级名称'")
  private String parentOrgName;

  @ApiModelProperty(value = "机构类型")
  @Column(columnDefinition = "varchar(128) comment '机构类型'")
  private String orgType;

  @ApiModelProperty(value = "机构地址")
  @Column(columnDefinition = "varchar(255) not null comment '机构地址'")
  private String orgAddress;

  @ApiModelProperty(value = "联系人")
  @Column(columnDefinition = "varchar(50) not null comment '联系人'")
  private String contacts;

  @ApiModelProperty(value = "联系方式")
  @Column(columnDefinition = "varchar(128) not null comment '联系方式'")
  private String tel;

}

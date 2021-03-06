package com.alsace.exchange.service.detection.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_environment_task_detail")
@ApiModel
@DynamicUpdate
public class EnvironmentTaskDetail extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @ApiModelProperty(value = "明细编码")
  @Column(columnDefinition = "varchar(128) not null comment '明细编码'")
  private String detailCode;

  @ApiModelProperty(value = "表单编码")
  @Column(columnDefinition = "varchar(128) comment '表单编码'")
  private String formCode;

  @ApiModelProperty(value = "任务编码")
  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

  @ApiModelProperty(value = "标签ID", required = true)
  @Column(columnDefinition = "bigint(20) not null comment '标签ID'")
  @NotBlank(groups = {Create.class}, message = "标签ID为空！")
  private Long tagId;

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

  @ApiModelProperty(value = "明细状态 10:创建 20:已提交")
  @Column(columnDefinition = "int(2) not null default 10 comment '明细状态 10:创建 20:已提交'")
  private Integer detailStatus;

  @ApiModelProperty(value = "检测时间")
  @Column(columnDefinition = "datetime comment '检测时间'")
  private Date detectionDate;

  /**
   * 标签名称 用于导入  数据库加上字段吧 展示不用关联了
   */
  @ApiModelProperty(value = "标签名称")
  @Column(columnDefinition = "varchar(255) comment '标签名称'")
  private String tagName;

  @ApiModelProperty(value = "试管编号")
  @Transient
  private String testTubeNo;

  @ApiModelProperty(value = "任务名称")
  @Transient
  private String taskName;

  @ApiModelProperty(value = "任务状态 10：已创建 20：待下发 30：已下发 40：待开始 50：进行中 60：已完成 70：取消", required = true)
  @Transient
  private Integer taskStatus;

  @ApiModelProperty(value = "任务开始时间",required = true)
  @Transient
  private Date startDate;

  @ApiModelProperty(value = "任务结束时间", required = true)
  @Transient
  private Date endDate;

  @Transient
  private List<EnvironmentTaskDetailResult> detailResultList;

  @ApiModelProperty(value = "表单状态 10:进行中 20:已提交")
  @Transient
  private Integer formStatus;
}

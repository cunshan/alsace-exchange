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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_person_task_detail")
@ApiModel
@DynamicUpdate
public class PersonTaskDetail extends BaseEntity {

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

  @ApiModelProperty(value = "任务地点ID", required = true)
  @Column(columnDefinition = "bigint(20) comment '任务地点ID'")
  private Long locationId;

  @ApiModelProperty(value = "姓名", required = true)
  @Column(columnDefinition = "varchar(128) not null comment '姓名'")
  @NotBlank(groups = {Create.class}, message = "姓名为空！")
  private String personName;

  @ApiModelProperty(value = "性别", required = true)
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

  @ApiModelProperty(value = "身份证号", required = true)
  @Column(columnDefinition = "varchar(64) not null comment '身份证号'")
  @NotBlank(groups = {Create.class}, message = "身份证号为空！")
  private String idCardNo;

  @ApiModelProperty(value = "地区")
  @Column(columnDefinition = "varchar(128) comment '地区'")
  private String region;

  @ApiModelProperty(value = "岗位")
  @Column(columnDefinition = "varchar(128) comment '岗位'")
  private String job;

  @ApiModelProperty(value = "电话", required = true)
  @Column(columnDefinition = "varchar(128) comment '电话'")
  @NotBlank(groups = {Create.class}, message = "电话为空！")
  private String tel;

  @ApiModelProperty(value = "企业名称")
  @Column(columnDefinition = "varchar(128) comment '企业名称'")
  private String companyName;

  @ApiModelProperty(value = "企业编码")
  @Column(columnDefinition = "varchar(128) comment '企业编码'")
  private String companyCode;

  @ApiModelProperty(value = "企业税号", required = true)
  @Column(columnDefinition = "varchar(128) comment '企业税号'")
  private String taxNo;

  @ApiModelProperty(value = "在职状态", required = true)
  @Column(columnDefinition = "tinyint(1) not null comment '在职状态'")
  @NotNull(groups = {Create.class}, message = "在职状态为空！")
  private Boolean working;

  @ApiModelProperty(value = "归属所编码")
  @Column(columnDefinition = "varchar(128) comment '归属所编码'")
  private String orgCode;

  @ApiModelProperty(value = "归属市")
  @Column(columnDefinition = "varchar(128) comment '归属市'")
  private String city;

  @ApiModelProperty(value = "明细状态 10:创建 20:已提交")
  @Column(columnDefinition = "int(2) not null default 10 comment '明细状态 10:创建 20:已提交'")
  private Integer detailStatus;


  @Transient
  private List<PersonTaskDetailResult> detailResultList;

  @ApiModelProperty(value = "样本类型 10:核酸 20:抗体")
  @Transient
  private String detailType;

  @ApiModelProperty(value = "照片URL")
  @Transient
  private String pictures;

  @ApiModelProperty(value = "检测结果状态 10:未检测 20:已提交")
  @Transient
  private Integer resultStatus;

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

  @ApiModelProperty(value = "试管编号")
  @Transient
  private String testTubeNo;

  @ApiModelProperty(value = "表单被检测人员数量")
  @Transient
  private long personCount;

  @ApiModelProperty(value = "检测类型数量")
  @Transient
  private List<Map> fromTypeCount;
}

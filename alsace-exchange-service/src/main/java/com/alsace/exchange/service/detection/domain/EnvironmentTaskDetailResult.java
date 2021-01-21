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
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "biz_environment_task_detail_result")
@ApiModel
@DynamicUpdate
public class EnvironmentTaskDetailResult extends BaseEntity {

  private static final long serialVersionUID = 2837596444087626503L;

  @ApiModelProperty(value = "任务编码")
  @Column(columnDefinition = "varchar(128) not null comment '任务编码'")
  private String taskCode;

  @ApiModelProperty(value = "明细编码")
  @Column(columnDefinition = "varchar(128) not null comment '明细编码'")
  private String detailCode;

  @ApiModelProperty(value = "采样管编码")
  @Column(columnDefinition = "varchar(128) comment '采样管编码'")
  @NotBlank(message = "采样管编码为空！")
  private String testTubeNo;

  @ApiModelProperty(value = "是否阳性")
  @Column(columnDefinition = "tinyint(1) comment '是否阳性'")
  private Boolean positive;

  @ApiModelProperty(value = "样本类型 10:食品 20:其他")
  @Column(columnDefinition = "varchar(32) not null default 10 comment '样本类型 10:食品 20:其他'")
  @NotBlank(message = "样本类型为空！")
  private String detailType;

  @ApiModelProperty(value = "照片URL")
  @Column(columnDefinition = "varchar(255) comment '照片URL'")
  private String pictures;

  //以下食品样本必填
  @ApiModelProperty(value = "产地")
  @Column(columnDefinition = "varchar(255) comment '产地'")
  private String manufacturePlace;

  @ApiModelProperty(value = "生产日期")
  @Column(columnDefinition = "datetime comment '生产日期'")
  private Date manufactureDate;

  @ApiModelProperty(value = "批号")
  @Column(columnDefinition = "varchar(255) comment '批号'")
  private String lotNumber;
  //以上食品样本必填

}

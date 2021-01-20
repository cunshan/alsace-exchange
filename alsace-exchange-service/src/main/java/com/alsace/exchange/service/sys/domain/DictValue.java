package com.alsace.exchange.service.sys.domain;

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
@Table(name = "sys_dict_data")
@ApiModel
@DynamicUpdate
public class DictValue extends BaseEntity {

  private static final long serialVersionUID = -6289769790229826482L;
  @ApiModelProperty(value = "数据字典值")
  @Column(columnDefinition = "varchar(128) not null comment '数据字典值'")
  private String valueCode;

  @ApiModelProperty(value = "数据字典值名称")
  @Column(columnDefinition = "varchar(255) not null comment '数据字典值名称'")
  private String valueLabel;

  @ApiModelProperty(value = "数据字典类型")
  @Column(columnDefinition = "varchar(128) not null comment '数据字典类型'")
  private String typeCode;

  @ApiModelProperty(value = "显示顺序")
  @Column(columnDefinition = "int(3) comment '显示顺序'")
  private Integer orderNum;

}

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

@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_dict_type")
@ApiModel
@DynamicUpdate
@Getter
@Setter
public class DictType extends BaseEntity {

  private static final long serialVersionUID = 2755183158140725692L;
  @ApiModelProperty(value = "数据字典编码")
  @Column(columnDefinition = "varchar(128) not null comment '数据字典编码'")
  private String dictCode;

  @ApiModelProperty(value = "数据字典名称")
  @Column(columnDefinition = "varchar(255) not null comment '数据字典名称'")
  private String dictName;

  @ApiModelProperty(value = "显示顺序")
  @Column(columnDefinition = "int(3) comment '显示顺序'")
  private Integer orderNum;


}

package com.alsace.exchange.service.user.domain;

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
@Table(name = "sys_role")
@ApiModel
@DynamicUpdate
public class Role extends BaseEntity {

  @ApiModelProperty("角色编码")
  @Column(columnDefinition = "varchar(64) not null comment '角色编码'")
  private String roleCode;

  @ApiModelProperty("角色名称")
  @Column(columnDefinition = "varchar(255) not null comment '角色名称'")
  private String roleName;


}

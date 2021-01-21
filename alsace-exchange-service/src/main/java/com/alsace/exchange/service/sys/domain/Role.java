package com.alsace.exchange.service.sys.domain;

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
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_role")
@ApiModel
@DynamicUpdate
public class Role extends BaseEntity {

  private static final long serialVersionUID = 1408143763293194610L;
  @ApiModelProperty("角色编码")
  @Column(columnDefinition = "varchar(64) not null comment '角色编码'")
  @NotBlank(groups = {Create.class},message = "角色编码为空！")
  private String roleCode;

  @ApiModelProperty("角色名称")
  @Column(columnDefinition = "varchar(255) not null comment '角色名称'")
  @NotBlank(groups = {Create.class},message = "角色名称为空！")
  private String roleName;


}

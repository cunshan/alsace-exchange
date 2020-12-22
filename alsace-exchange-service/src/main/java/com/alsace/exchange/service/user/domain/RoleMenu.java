package com.alsace.exchange.service.user.domain;

import com.alsace.exchange.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_role_menu")
@ApiModel
public class RoleMenu extends BaseEntity {

  @Column(columnDefinition = "varchar(64) not null comment '角色编码'")
  private String roleCode;

  @Column(columnDefinition = "bigint(20) not null comment '菜单ID'")
  private Long menuId;

}

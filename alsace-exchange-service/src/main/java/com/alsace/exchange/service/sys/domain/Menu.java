package com.alsace.exchange.service.sys.domain;

import com.alsace.exchange.common.base.BaseEntity;
import com.alsace.exchange.common.validate.groups.Create;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_menu")
@ApiModel
@DynamicUpdate
public class Menu extends BaseEntity {
  private static final long serialVersionUID = 7365386415095687241L;

  @ApiModelProperty(value = "菜单名称")
  @Column(columnDefinition = "varchar(255) not null comment '菜单名称'")
  @NotBlank(groups = {Create.class},message = "菜单名称为空！")
  private String menuName;

  @ApiModelProperty(value = "父菜单ID")
  @Column(columnDefinition = "bigint(20) comment '父菜单ID'")
  private Long parentId;

  @ApiModelProperty(value = "显示顺序")
  @Column(columnDefinition = "int(3) comment '显示顺序'")
  private Integer orderNum;

  @ApiModelProperty(value = "路由地址")
  @Column(columnDefinition = "varchar(255) comment '路由地址'")
  private String path;

  @ApiModelProperty(value = "重定向地址")
  @Column(columnDefinition = "varchar(255) comment '重定向地址'")
  private String redirectPath;

//  @ApiModelProperty(value = "组件路径")
//  @Column(columnDefinition = "varchar(255) comment '组件路径'")
//  private String component;

  @ApiModelProperty(value = "是否为外链（0是 1否）")
  @Column(columnDefinition = "tinyint(1) not null comment '是否为外链（0是 1否）'")
  @NotNull(groups = {Create.class},message = "是否为外链为空！")
  private Boolean isFrame;

  @ApiModelProperty(value = "菜单类型（D目录 M菜单 B按钮）")
  @Column(columnDefinition = "varchar(32) not null comment '菜单类型（D目录 M菜单 B按钮）'")
  @NotBlank(groups = {Create.class},message = "菜单类型为空！")
  private String menuType;

  @ApiModelProperty(value = "菜单状态（0显示 1隐藏）")
  @Column(columnDefinition = "tinyint(1) not null default 0 comment '菜单状态（0显示 1隐藏）'")
  @NotNull(groups = {Create.class},message = "菜单状态为空！")
  private Boolean visible;

  @ApiModelProperty(value = "权限标识")
  @Column(columnDefinition = "varchar(255) comment '权限标识'")
  private String perms;

  @ApiModelProperty(value = "菜单图标")
  @Column(columnDefinition = "varchar(255) comment '菜单图标'")
  private String icon;

}

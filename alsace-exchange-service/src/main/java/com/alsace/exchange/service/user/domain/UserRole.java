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
@Table(name = "sys_user_role")
@ApiModel
public class UserRole extends BaseEntity {
  private static final long serialVersionUID = 7419640927791127683L;

  @Column(columnDefinition = "varchar(32) not null comment '登录账号'")
  private String loginAccount;

  @Column(columnDefinition = "varchar(64) not null comment '角色编码'")
  private String roleCode;



}

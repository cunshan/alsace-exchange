package com.alsace.exchange.service.sys.domain;

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
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_user_data")
@ApiModel
public class UserData extends BaseEntity {
  private static final long serialVersionUID = 7419640927791127683L;

  @Column(columnDefinition = "varchar(32) not null comment '登录账号'")
  @NotBlank(message = "登录账号为空！")
  private String loginAccount;

  @Column(columnDefinition = "varchar(64) not null comment '数据类型'")
  @NotBlank(message = "数据类型为空！")
  private String dataType;

  @Column(columnDefinition = "varchar(64) not null comment '数据编码'")
  @NotBlank(message = "数据编码为空！")
  private String dataCode;

  @Column(columnDefinition = "varchar(64) not null comment '数据显示名称'")
  @NotBlank(message = "数据显示名称为空！")
  private String dataLabel;


}

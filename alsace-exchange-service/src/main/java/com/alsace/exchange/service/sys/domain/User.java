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

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_user")
@ApiModel
@DynamicUpdate
public class User extends BaseEntity {

    private static final long serialVersionUID = 5385740476210184698L;

    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    @Column(columnDefinition = "varchar(32) not null comment '登录名'")
    @NotBlank(groups = {Create.class}, message = "登录名为空！")
    private String loginAccount;
    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    @Column(columnDefinition = "varchar(32) not null comment '用户姓名'")
    @NotBlank(groups = {Create.class}, message = "用户姓名为空！")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @Column(columnDefinition = "varchar(64) not null comment '密码'")
    @NotBlank(groups = {Create.class}, message = "密码为空！")
    private String password;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @Column(columnDefinition = "varchar(64) comment '昵称'")
    private String nickName;
    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    @Column(columnDefinition = "varchar(64) comment '电话号码'")
    @NotBlank(groups = {Create.class}, message = "电话号码为空！")
    private String tel;
    /**
     * 邮件地址
     */
    @ApiModelProperty("邮件地址")
    @Column(columnDefinition = "varchar(64) comment '邮件地址'")
    private String email;

    /**
     * 锁定标记
     */
    @ApiModelProperty("锁定标记")
    @Column(columnDefinition = "tinyint(1) not null default 0 comment '锁定标记'")
    private Boolean locked;


}

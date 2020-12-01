package com.alsace.exchange.user.domain;

import com.alsace.exchange.common.base.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "sys_user")
public class User extends BaseDomain {

    private static final long serialVersionUID = 5385740476210184698L;

    /**
     * 登录名
     */
    @Column(columnDefinition = "varchar(32) not null comment '登录名'")
    private String loginAccount;
    /**
     * 用户姓名
     */
    @Column(columnDefinition = "varchar(32) not null comment '用户姓名'")
    private String userName;
    /**
     * 密码
     */
    @Column(columnDefinition = "varchar(64) not null comment '密码'")
    private String password;
    /**
     * 昵称
     */
    @Column(columnDefinition = "varchar(64) comment '昵称'")
    private String nickName;
    /**
     * 电话号码
     */
    @Column(columnDefinition = "varchar(64) comment '电话号码'")
    private String tel;
    /**
     * 邮件地址
     */
    @Column(columnDefinition = "varchar(64) comment '邮件地址'")
    private String email;

    /**
     * 锁定标记
     */
    @Column(columnDefinition = "tinyint(1) not null default 0 comment '锁定标记'")
    private Boolean locked;


}

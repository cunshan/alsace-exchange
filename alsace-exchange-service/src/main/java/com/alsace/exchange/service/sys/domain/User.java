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
@Table(name = "sys_user")
@ApiModel
@DynamicUpdate
public class User extends BaseEntity {

    private static final long serialVersionUID = 5385740476210184698L;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名",required = true)
    @Column(columnDefinition = "varchar(32) not null comment '登录名'")
    @NotBlank(groups = {Create.class}, message = "登录名为空！")
    private String loginAccount;
    /**
     * 用户姓名
     */
    @ApiModelProperty(value = "用户姓名",required = true)
    @Column(columnDefinition = "varchar(32) not null comment '用户姓名'")
    @NotBlank(groups = {Create.class}, message = "用户姓名为空！")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
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
    @ApiModelProperty(value = "电话号码",required = true)
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

    @ApiModelProperty(value = "性别")
    @Column(columnDefinition = "int(1) comment '性别 0 男性  1女性'")
    private Integer gender;

    @ApiModelProperty(value = "民族")
    @Column(columnDefinition = "varchar(128) comment '民族'")
    private String nation;

    @ApiModelProperty(value = "出生日期")
    @Column(columnDefinition = "varchar(32) comment '出生日期'")
    private String birthday;

    @ApiModelProperty(value = "住址")
    @Column(columnDefinition = "varchar(255) comment '住址'")
    private String address;

    @ApiModelProperty(value = "身份证号")
    @Column(columnDefinition = "varchar(64) comment '身份证号'")
    private String idCardNo;

    @ApiModelProperty(value = "地区")
    @Column(columnDefinition = "varchar(128) comment '地区'")
    private String region;

    @ApiModelProperty(value = "岗位")
    @Column(columnDefinition = "varchar(128) comment '岗位'")
    private String job;

    @ApiModelProperty(value = "在职状态")
    @Column(columnDefinition = "tinyint(1) not null comment '在职状态'")
    private Boolean working;

    @ApiModelProperty(value = "归属所编码")
    @Column(columnDefinition = "varchar(128) comment '归属所编码'")
    private String orgCode;

    @ApiModelProperty(value = "归属市")
    @Column(columnDefinition = "varchar(128) comment '归属市'")
    private String city;

}

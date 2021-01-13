package com.alsace.exchange.service.sys.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserImport implements IExcelDataModel,IExcelModel, Serializable {

    private static final long serialVersionUID = 5385740476210184698L;

    /**
     * 登录名
     */
    @ApiModelProperty("登录名")
    @Excel(name = "登录名", isImportField = "true")
    private String loginAccount;
    /**
     * 用户姓名
     */
    @ApiModelProperty("用户姓名")
    @Excel(name = "登录名", isImportField = "true")
    private String userName;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    @Excel(name = "登录名", isImportField = "false")
    private String password;
    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @Excel(name = "登录名", isImportField = "true")
    private String nickName;
    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    @Excel(name = "登录名", isImportField = "true")
    private String tel;
    /**
     * 邮件地址
     */
    @ApiModelProperty("邮件地址")
    @Excel(name = "登录名", isImportField = "false")
    private String email;

    /**
     * 锁定标记
     */
    @ApiModelProperty("锁定标记")
    @Excel(name = "锁定标记", isImportField = "false")
    private Boolean locked;


    @Override
    public int getRowNum() {
        return 0;
    }

    @Override
    public void setRowNum(int i) {

    }

    @Override
    public String getErrorMsg() {
        return null;
    }

    @Override
    public void setErrorMsg(String s) {

    }
}

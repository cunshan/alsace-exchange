package com.alsace.exchange.service.sys.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserImport implements IExcelDataModel,IExcelModel, Serializable {

    private static final long serialVersionUID = 1385740476210184698L;

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
    @Excel(name = "用户姓名", isImportField = "true")
    private String userName;
    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    @Excel(name = "电话号码", isImportField = "true")
    private String tel;

    private int rowNum;
    private String errorMsg;
}

package com.alsace.exchange.service.detection.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonTaskDetailImport implements IExcelDataModel, IExcelModel, Serializable {


  @ApiModelProperty(value = "姓名", required = true)
  @Excel(name = "姓名", isImportField = "true")
  private String personName;

  @ApiModelProperty(value = "性别", required = true)
  @Excel(name = "性别", isImportField = "true")
  private String gender;

  @ApiModelProperty(value = "民族")
  @Excel(name = "民族", isImportField = "true")
  private String nation;

  @ApiModelProperty(value = "出生日期")
  @Excel(name = "出生日期", isImportField = "true")
  private String birthday;

  @ApiModelProperty(value = "住址")
  @Excel(name = "住址", isImportField = "true")
  private String address;

  @ApiModelProperty(value = "身份证号", required = true)
  @Excel(name = "身份证号", isImportField = "true")
  private String idCardNo;

  @ApiModelProperty(value = "地区")
  @Excel(name = "地区", isImportField = "true")
  private String region;

  @ApiModelProperty(value = "岗位")
  @Excel(name = "岗位", isImportField = "true")
  private String job;

  @ApiModelProperty(value = "电话", required = true)
  @Excel(name = "电话", isImportField = "true")
  private String tel;

  @ApiModelProperty(value = "企业名称", required = true)
  @Excel(name = "企业名称", isImportField = "true")
  private String companyName;

  @ApiModelProperty(value = "企业编码", required = true)
  @Excel(name = "企业编码", isImportField = "true")
  private String companyCode;

  @ApiModelProperty(value = "企业税号", required = true)
  @Excel(name = "企业税号", isImportField = "true")
  private String taxNo;

  @ApiModelProperty(value = "在职状态", required = true)
  @Excel(name = "在职状态", isImportField = "true")
  private String working;

  @ApiModelProperty(value = "归属市")
  @Excel(name = "归属市", isImportField = "true")
  private String city;

  @ApiModelProperty(value = "检测类型")
  private String detailType;

  @ApiModelProperty(value = "采样编码")
  private String testTubeNo;

  @ApiModelProperty(value = "检测结果")
  private String positive;

  private int rowNum;
  private String errorMsg;
}

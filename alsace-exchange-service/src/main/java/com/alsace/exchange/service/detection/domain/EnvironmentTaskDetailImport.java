package com.alsace.exchange.service.detection.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class EnvironmentTaskDetailImport implements IExcelDataModel, IExcelModel, Serializable {

  private static final long serialVersionUID = 2347596444082126503L;

  @ApiModelProperty(value = "企业编码")
  @Excel(name = "企业编码")
  private String companyCode;

  @ApiModelProperty(value = "企业名称")
  @Excel(name = "企业名称", isImportField = "true")
  private String companyName;

  @ApiModelProperty(value = "行业类别")
  @Excel(name = "行业类别")
  private String category;

  @ApiModelProperty(value = "营业范围")
  @Excel(name = "营业范围")
  private String scope;

  @ApiModelProperty(value = "地址")
  @Excel(name = "地址")
  private String address;

  @ApiModelProperty(value = "联系方式")
  @Excel(name = "联系方式")
  private String tel;

  @ApiModelProperty(value = "负责人")
  @Excel(name = "负责人")
  private String chargePerson;

  @ApiModelProperty(value = "负责人身份证号")
  @Excel(name = "负责人身份证号")
  private String chargePersonIdCard;

  @ApiModelProperty(value = "税号")
  @Excel(name = "税号", isImportField = "true")
  private String taxCode;

  @ApiModelProperty(value = "归属市")
  @Excel(name = "归属市")
  private String city;

  @ApiModelProperty(value = "标签名称")
  @Excel(name = "标签名称", isImportField = "true")
  private String tagName;

  private int rowNum;
  private String errorMsg;
}

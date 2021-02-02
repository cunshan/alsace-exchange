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

  private static final long serialVersionUID = 2347596444087626503L;

  @ApiModelProperty(value = "标签ID", required = true)
  @Column(columnDefinition = "bigint(20) not null comment '标签ID'")
  @Excel(name = "标签ID", isImportField = "true")
  private Long tagId;

  @ApiModelProperty(value = "企业编码")
  @Excel(name = "企业编码", isImportField = "true")
  private String companyCode;

  @ApiModelProperty(value = "企业名称")
  @Excel(name = "企业名称", isImportField = "true")
  private String companyName;

  @ApiModelProperty(value = "行业类别")
  @Excel(name = "行业类别", isImportField = "true")
  private String category;

  @ApiModelProperty(value = "营业范围")
  @Excel(name = "营业范围", isImportField = "true")
  private String scope;

  @ApiModelProperty(value = "地址")
  @Excel(name = "地址", isImportField = "true")
  private String address;

  @ApiModelProperty(value = "联系方式")
  @Excel(name = "联系方式", isImportField = "true")
  private String tel;

  @ApiModelProperty(value = "负责人")
  @Excel(name = "负责人", isImportField = "true")
  private String chargePerson;

  @ApiModelProperty(value = "负责人身份证号")
  @Excel(name = "负责人身份证号", isImportField = "true")
  private String chargePersonIdCard;

  @ApiModelProperty(value = "税号")
  @Excel(name = "税号", isImportField = "true")
  private String taxCode;

  @ApiModelProperty(value = "归属所编码")
  @Excel(name = "归属所编码", isImportField = "true")
  private String orgCode;

  @ApiModelProperty(value = "归属市")
  @Excel(name = "归属市", isImportField = "true")
  private String city;

  @ApiModelProperty(value = "标签名称")
  @Excel(name = "标签名称", isImportField = "true")
  private String tagName;

  private int rowNum;
  private String errorMsg;
}

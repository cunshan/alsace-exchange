package com.alsace.exchange.service.detection.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import com.alsace.exchange.common.base.BaseEntity;
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
import java.io.Serializable;

@Data
public class DetectionOrgImport implements IExcelDataModel, IExcelModel, Serializable {

  @ApiModelProperty(value = "机构名称")
  @Excel(name = "机构名称", isImportField = "true")
  private String orgName;

  @ApiModelProperty(value = "机构编码")
  @Excel(name = "机构编码", isImportField = "true")
  private String orgCode;

  @ApiModelProperty(value = "机构地址")
  @Excel(name = "机构地址", isImportField = "true")
  private String orgAddress;

  @ApiModelProperty(value = "联系人")
  @Excel(name = "联系人", isImportField = "true")
  private String contacts;

  @ApiModelProperty(value = "联系方式")
  @Excel(name = "联系方式", isImportField = "true")
  private String tel;

  private int rowNum;
  private String errorMsg;
}
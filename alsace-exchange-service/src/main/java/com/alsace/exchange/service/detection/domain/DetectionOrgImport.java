package com.alsace.exchange.service.detection.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
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
import java.io.Serializable;

@Data
public class DetectionOrgImport implements IExcelDataModel, IExcelModel, Serializable {

  @ApiModelProperty(value = "机构名称")
  @Excel(name = "机构名称", isImportField = "true")
  private String orgName;

  @ApiModelProperty(value = "机构类型")
  @Excel(name = "机构类型（1：检测机构、2：被检测机构）", isImportField = "true")
  private String orgType;

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

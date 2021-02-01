package com.alsace.exchange.common.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
@Accessors(chain = true)
@DynamicUpdate
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -4251104853104709042L;
    /**
     * ID
     */
    @ApiModelProperty("ID")
    @Id
    @Column(columnDefinition = "bigint(20) comment 'ID'")
    private Long id;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @Column(columnDefinition = "varchar(32) comment '创建人'")
    private String createdBy;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @Column(columnDefinition = "datetime comment '创建时间'")
    private Date createdDate;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    @Column(columnDefinition = "varchar(32) comment '更新人'")
    private String modifiedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @Column(columnDefinition = "datetime comment '更新时间'")
    private Date modifiedDate;
    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @Column(columnDefinition = "varchar(255) comment '备注'")
    private String remarks;

    /**
     * 删除标记
     */
    @ApiModelProperty("删除标记")
    @Column(columnDefinition="tinyint(1) not null default 0 comment '删除标记'")
    private Boolean deleted;

    @Transient
    private List<CodeName> userDataList = new ArrayList<>();
    @Transient
    private String userDataAccount;

}

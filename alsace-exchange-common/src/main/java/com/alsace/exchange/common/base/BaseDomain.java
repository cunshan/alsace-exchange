package com.alsace.exchange.common.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = -4251104853104709042L;
    /**
     * ID
     */
    @Id
    @Column(columnDefinition = "bigint(20) comment 'ID'")
    private Long id;
    /**
     * 创建人
     */
    @Column(columnDefinition = "varchar(32) comment '创建人'")
    private String createdBy;
    /**
     * 创建时间
     */
    @Column(columnDefinition = "datetime comment '创建时间'")
    private Date createdDate;
    /**
     * 更新人
     */
    @Column(columnDefinition = "varchar(32) comment '更新人'")
    private String updatedBy;
    /**
     * 更新时间
     */
    @Column(columnDefinition = "datetime comment '更新时间'")
    private Date updatedDate;
    /**
     * 备注
     */
    @Column(columnDefinition = "varchar(255) comment '备注'")
    private String remarks;

    /**
     * 删除标记
     */
    @Column(columnDefinition="tinyint(1) not null default 0 comment '删除标记'")
    private Boolean deleted;

}

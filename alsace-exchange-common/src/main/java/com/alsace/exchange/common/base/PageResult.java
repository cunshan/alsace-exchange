package com.alsace.exchange.common.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页
 */
@Data
@Accessors(chain = true)
public class PageResult<T> implements Serializable {
  private static final long serialVersionUID = -4233314831829280008L;

  /**
   * 页码
   */
  private int pageNum = 1;

  /**
   * 每页条数
   */
  private int pageSize = 10;

  /**
   * 总页数
   */
  private int totalPages;

  /**
   * 总条数
   */
  private long totalCount;

  /**
   * 列表数据
   */
  private List<T> records;

  public PageResult(){
  }

  public PageResult(Page<T> page){
    this.totalCount = page.getTotalElements();
    //jpa分页是从0开始的
    this.pageNum = page.getNumber()+1;
    this.pageSize = page.getSize();
    this.records = page.getContent();
    this.totalPages = page.getTotalPages();
  }
}

package com.alsace.exchange.service.detection.mapper;

import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonTaskDetailMapper {

    /**
     * 人员检测结果查询分页
     * @Author: wayne
     * @Date: 2021/1/30
     */
    List<PersonTask> findResultPage(@Param("param") PersonTaskDetail param);

    /**
     * 人员检测结果条件查询
     * @Author: wayne
     * @Date: 2021/1/30
     */
    List<PersonTaskDetailImport> findResults(@Param("param") PersonTaskDetail param);
}

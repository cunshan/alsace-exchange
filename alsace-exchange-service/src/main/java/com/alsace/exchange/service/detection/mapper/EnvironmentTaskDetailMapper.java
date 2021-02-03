package com.alsace.exchange.service.detection.mapper;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailImport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnvironmentTaskDetailMapper {

    /**
     * 环境检测结果查询分页
     * @Author: wayne
     * @Date: 2021/1/30
     */
    List<EnvironmentTaskDetail> findResultPage(@Param("param") EnvironmentTaskDetail param);

    /**
     * 环境检测结果条件查询
     * @Author: wayne
     * @Date: 2021/1/30
     */
    List<EnvironmentTaskDetailImport> findResults(@Param("param") EnvironmentTaskDetail param);
}

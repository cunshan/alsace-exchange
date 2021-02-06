package com.alsace.exchange.service.detection.mapper;

import com.alsace.exchange.service.detection.domain.PersonDetectionMethodCount;
import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailImport;
import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 查询人员表单信息分页
     * @param param 查询条件
     * @Author: wayne
     * @Date: 2021/2/1
     */
    List<PersonTaskDetailImport> findFromPage(@Param("param")PersonTaskDetail param);

    /**
     * 查询表单已提交采样数量 根据类型分组
     * @param formCode 表单编码
     * @Author: wayne
     * @Date: 2021/2/5
     */
    List<Map> findDetailTypeCount(@Param("formCode")String formCode);

  /**
   * 按照检测项目分组统计
   */
  List<PersonDetectionMethodCount> findMethodCount(@Param("taskCode") String taskCode,@Param("formCode") String formCode);
}

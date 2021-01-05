package com.alsace.exchange.service.detection.mapper;

import com.alsace.exchange.service.detection.domain.PersonTaskApp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PersonTaskMapper {

  @Select("select t.task_code as taskCode,t.task_desc as taskDesc," +
      "t.task_status as taskStatus,t.start_date as startDate,t.end_date as endDate," +
      "t.detection_type as detectionType,t.detection_method as detectionMethod," +
      "t.mixed_mode as mixedMode,l.id as locationId,l.location_name as locationName " +
      "from biz_person_task t left join biz_person_task_location l on t.task_code = l.task_code " +
      "left join biz_person_task_operator o on o.location_id = l.id and o.task_code=t.task_code and o.tel = #{loginAccount} " +
      "where t.deleted='0' " +
      "and l.deleted='0' " +
      "and o.deleted='0' ")
  List<PersonTaskApp> selectAppTaskList(@Param("loginAccount") String loginAccount);

}

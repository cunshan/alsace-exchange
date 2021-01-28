package com.alsace.exchange.service.detection.mapper;

import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PersonTaskDetailMapper {

  List<PersonTask> findResultPage(@Param("param") PersonTaskDetail param);
}

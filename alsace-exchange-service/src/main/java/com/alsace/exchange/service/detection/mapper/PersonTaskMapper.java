package com.alsace.exchange.service.detection.mapper;

import com.alsace.exchange.service.detection.domain.PersonTask;
import com.alsace.exchange.service.detection.domain.PersonTaskApp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PersonTaskMapper {


  List<PersonTaskApp> selectAppTaskList(@Param("loginAccount") String loginAccount);


  List<PersonTask> findPage(@Param("param") PersonTask param);
}

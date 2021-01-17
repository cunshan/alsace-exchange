package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonTaskDetailRepository extends JpaRepository<PersonTaskDetail,Long>, JpaSpecificationExecutor<PersonTaskDetail> {

  @Modifying(clearAutomatically = true,flushAutomatically = true)
  @Query(value = "update biz_person_task_detail set nucleic_acid_positive=?2,antibody_positive=?2 where task_code IN (?1)",nativeQuery = true)
  void updateResult(List<String> taskCodeList, boolean positive);

}

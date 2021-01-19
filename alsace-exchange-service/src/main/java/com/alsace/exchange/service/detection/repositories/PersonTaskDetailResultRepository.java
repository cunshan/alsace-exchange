package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonTaskDetailResultRepository extends JpaRepository<PersonTaskDetailResult,Long>, JpaSpecificationExecutor<PersonTaskDetailResult> {

  @Modifying(clearAutomatically = true,flushAutomatically = true)
  @Query(value = "update biz_person_task_detail_result set positive=?2 where detail_code IN(?1)",nativeQuery = true)
  void updateResultByTaskCode(List<String> taskCodeList, boolean positive);

  @Modifying(clearAutomatically = true,flushAutomatically = true)
  @Query(value = "update biz_person_task_detail_result set positive=?4 where task_code = ?1 and test_tube_no=?2 and detail_type=?3",nativeQuery = true)
  int updateResultByTubeNo(String taskCode, String testTubeNo, String detectionType, Boolean positive);
}

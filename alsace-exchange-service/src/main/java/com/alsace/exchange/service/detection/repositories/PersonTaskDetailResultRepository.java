package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskDetailResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonTaskDetailResultRepository extends JpaRepository<PersonTaskDetailResult,Long>, JpaSpecificationExecutor<PersonTaskDetailResult> {

  @Modifying(clearAutomatically = true,flushAutomatically = true)
  @Query(value = "update biz_person_task_detail_result set result_status=20,positive=?2,created_by=?3,created_date=now() where detail_code IN(?1) and result_status=10",nativeQuery = true)
  void updateResultByTaskCode(List<String> taskCodeList, boolean positive,String loginAccount);

  @Modifying(clearAutomatically = true,flushAutomatically = true)
  @Query(value = "update biz_person_task_detail_result set positive=?4,created_by=?5,created_date=now() where task_code = ?1 and test_tube_no=?2 and detail_type=?3 and result_status=10",nativeQuery = true)
  int updateResultByTubeNo(String taskCode, String testTubeNo, String detectionType, Boolean positive,String loginAccount);
}

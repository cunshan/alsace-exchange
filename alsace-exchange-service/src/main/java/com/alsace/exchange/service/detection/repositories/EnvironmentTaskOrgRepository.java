package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnvironmentTaskOrgRepository extends JpaRepository<EnvironmentTaskOrg,Long>, JpaSpecificationExecutor<EnvironmentTaskOrg> {

  /**
   * 根据任务编码删除所有
   */
  void deleteAllByTaskCode(String taskCode);
}

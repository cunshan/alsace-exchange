package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnvironmentTaskTagRepository extends JpaRepository<EnvironmentTaskTag,Long>, JpaSpecificationExecutor<EnvironmentTaskTag> {

  /**
   * 根据任务编码删除所有
   */
  void deleteAllByTaskCode(String taskCode);

}

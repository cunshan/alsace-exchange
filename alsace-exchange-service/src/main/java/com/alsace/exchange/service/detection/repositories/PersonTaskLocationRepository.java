package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonTaskLocationRepository extends JpaRepository<PersonTaskLocation,Long>, JpaSpecificationExecutor<PersonTaskLocation> {

  /**
   * 根据任务编码删除所有
   */
  void deleteAllByTaskCode(String taskCode);

}

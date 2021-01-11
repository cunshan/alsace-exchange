package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetailResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnvironmentTaskDetailResultRepository extends JpaRepository<EnvironmentTaskDetailResult,Long>, JpaSpecificationExecutor<EnvironmentTaskDetailResult> {

}

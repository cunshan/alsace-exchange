package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnvironmentTaskDetailRepository extends JpaRepository<EnvironmentTaskDetail,Long>, JpaSpecificationExecutor<EnvironmentTaskDetail> {
}

package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.EnvironmentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnvironmentTaskRepository extends JpaRepository<EnvironmentTask,Long>, JpaSpecificationExecutor<EnvironmentTask> {

}

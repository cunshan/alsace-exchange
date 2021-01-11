package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnvironmentTaskFormRepository extends JpaRepository<EnvironmentTaskForm,Long>, JpaSpecificationExecutor<EnvironmentTaskForm> {
}

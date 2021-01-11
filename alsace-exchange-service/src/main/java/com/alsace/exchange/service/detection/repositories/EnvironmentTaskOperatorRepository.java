package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.EnvironmentTaskOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EnvironmentTaskOperatorRepository extends JpaRepository<EnvironmentTaskOperator,Long>, JpaSpecificationExecutor<EnvironmentTaskOperator> {
}

package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskDetectionMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonTaskDetectionMethodRepository extends JpaRepository<PersonTaskDetectionMethod,Long>, JpaSpecificationExecutor<PersonTaskDetectionMethod> {
}

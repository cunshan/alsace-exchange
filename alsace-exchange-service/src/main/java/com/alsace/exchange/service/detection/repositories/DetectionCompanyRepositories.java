package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.DetectionCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetectionCompanyRepositories extends JpaRepository<DetectionCompany,Long>, JpaSpecificationExecutor<DetectionCompany> {
}

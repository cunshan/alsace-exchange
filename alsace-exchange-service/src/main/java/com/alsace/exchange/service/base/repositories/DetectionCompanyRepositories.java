package com.alsace.exchange.service.base.repositories;

import com.alsace.exchange.service.base.domain.DetectionCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetectionCompanyRepositories extends JpaRepository<DetectionCompany,Long>, JpaSpecificationExecutor<DetectionCompany> {
}

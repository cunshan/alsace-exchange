package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.DetectionOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetectionOrgRepository extends JpaRepository<DetectionOrg,Long>, JpaSpecificationExecutor<DetectionOrg> {
}

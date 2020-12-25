package com.alsace.exchange.service.base.repositories;

import com.alsace.exchange.service.base.domain.DetectionOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetectionOrgRepositories extends JpaRepository<DetectionOrg,Long>, JpaSpecificationExecutor<DetectionOrg> {
}

package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.DetectionPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetectionPersonRepositories extends JpaRepository<DetectionPerson,Long>, JpaSpecificationExecutor<DetectionPerson> {
}

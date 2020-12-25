package com.alsace.exchange.service.base.repositories;

import com.alsace.exchange.service.base.domain.DetectionPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DetectionPersonRepositories extends JpaRepository<DetectionPerson,Long>, JpaSpecificationExecutor<DetectionPerson> {
}

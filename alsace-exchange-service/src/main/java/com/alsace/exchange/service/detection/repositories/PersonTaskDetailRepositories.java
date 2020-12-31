package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonTaskDetailRepositories extends JpaRepository<PersonTaskDetail,Long>, JpaSpecificationExecutor<PersonTaskDetail> {
}

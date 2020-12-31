package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonTaskRepositories extends JpaRepository<PersonTask,Long>, JpaSpecificationExecutor<PersonTask> {
}

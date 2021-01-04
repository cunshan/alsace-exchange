package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonTaskFormRepository extends JpaRepository<PersonTaskForm,Long>, JpaSpecificationExecutor<PersonTaskForm> {
}

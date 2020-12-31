package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonTaskOperatorRepositories extends JpaRepository<PersonTaskOperator,Long>, JpaSpecificationExecutor<PersonTaskOperator> {
}

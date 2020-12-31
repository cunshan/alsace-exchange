package com.alsace.exchange.service.detection.repositories;

import com.alsace.exchange.service.detection.domain.PersonTaskOrg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PersonTaskOrgRepository extends JpaRepository<PersonTaskOrg,Long>, JpaSpecificationExecutor<PersonTaskOrg> {
}

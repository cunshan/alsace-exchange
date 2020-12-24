package com.alsace.exchange.service.sys.repositories;

import com.alsace.exchange.service.sys.domain.DictType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DictTypeRepository extends JpaRepository<DictType,Long>, JpaSpecificationExecutor<DictType> {

}

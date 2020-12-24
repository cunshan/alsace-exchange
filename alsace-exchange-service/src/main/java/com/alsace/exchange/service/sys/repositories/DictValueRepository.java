package com.alsace.exchange.service.sys.repositories;

import com.alsace.exchange.service.sys.domain.DictValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DictValueRepository extends JpaRepository<DictValue,Long>, JpaSpecificationExecutor<DictValue> {

}

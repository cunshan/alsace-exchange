package com.alsace.exchange.service.sys.repositories;

import com.alsace.exchange.service.sys.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long>, JpaSpecificationExecutor<Menu> {

  @Query(value = "select t.* from Menu t where t.parent_id is null and t.deleted = '0' ",nativeQuery = true)
  List<Menu> findAllRoot();
}

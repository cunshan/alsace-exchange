package com.alsace.exchange.service.sys.repositories;

import com.alsace.exchange.service.sys.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long>, JpaSpecificationExecutor<Menu> {

  @Query(value = "select t.* from sys_menu t,sys_role_menu m where m.menu_id=t.id and t.deleted = '0' and m.deleted='0' and m.role_code IN(?1) ", nativeQuery = true)
  List<Menu> findByRoleCode(List<String> roleCodeList);


}

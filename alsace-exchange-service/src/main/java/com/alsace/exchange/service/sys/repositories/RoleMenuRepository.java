package com.alsace.exchange.service.sys.repositories;

import com.alsace.exchange.service.sys.domain.RoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleMenuRepository extends JpaRepository<RoleMenu,Long>, JpaSpecificationExecutor<RoleMenu> {

}

package com.alsace.exchange.service.user.repositories;

import com.alsace.exchange.service.user.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MenuRepository extends JpaRepository<Menu,Long>, JpaSpecificationExecutor<Menu> {

}

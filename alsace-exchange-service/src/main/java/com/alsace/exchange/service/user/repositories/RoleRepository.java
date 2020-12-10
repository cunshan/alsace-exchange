package com.alsace.exchange.service.user.repositories;

import com.alsace.exchange.service.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {

}

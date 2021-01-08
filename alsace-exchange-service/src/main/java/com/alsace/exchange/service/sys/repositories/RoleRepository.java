package com.alsace.exchange.service.sys.repositories;

import com.alsace.exchange.service.sys.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {

  @Query(value = "select t.* from sys_role t,sys_user_role m where t.role_code=m.role_code and t.deleted = '0' and m.deleted='0' and m.login_account=?1", nativeQuery = true)
  List<Role> findByLoginAccount(String loginAccount);
}

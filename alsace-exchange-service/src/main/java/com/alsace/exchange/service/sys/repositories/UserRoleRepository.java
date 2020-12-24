package com.alsace.exchange.service.sys.repositories;

import com.alsace.exchange.service.sys.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRoleRepository extends JpaRepository<UserRole,Long>, JpaSpecificationExecutor<UserRole> {

  /**
   * 删除登录账号对应的所有角色
   */
  void deleteAllByLoginAccount(String loginAccount);
}

package com.alsace.exchange.service.sys.repositories;

import com.alsace.exchange.service.sys.domain.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDataRepository extends JpaRepository<UserData,Long>, JpaSpecificationExecutor<UserData> {

  void deleteAllByLoginAccount(String loginAccount);
}

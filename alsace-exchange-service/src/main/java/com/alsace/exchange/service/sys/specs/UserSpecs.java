package com.alsace.exchange.service.sys.specs;

import com.alsace.exchange.service.sys.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户查询条件
 */
public class UserSpecs {


  /**
   * loginAccount  in
   */
  public static Specification<User> loginAccountIn(List<String> accountList) {
    return (root, query, builder) -> builder.in(root.get("loginAccount")).value(accountList);
  }

  /**
   * deleted
   */
  public static Specification<User> deleted(Boolean deleted) {
    return (root, query, builder) -> builder.equal(root.get("deleted"), deleted);
  }

}

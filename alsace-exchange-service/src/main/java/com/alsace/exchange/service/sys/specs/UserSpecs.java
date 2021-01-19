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

  public static Specification<User> build(User user) {
    return (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      predicateList.add(cb.equal(root.get("deleted"), false));
      if (!StringUtils.isBlank(user.getTel())) {
        predicateList.add(cb.equal(root.get("tel"), user.getTel()));
      }
      if (!StringUtils.isBlank(user.getUserName())) {
        predicateList.add(cb.like(root.get("userName"), user.getUserName() + "%"));
      }
      if (!StringUtils.isBlank(user.getLoginAccount())) {
        predicateList.add(cb.equal(root.get("loginAccount"), user.getLoginAccount()));
      }
      if (!StringUtils.isBlank(user.getNickName())) {
        predicateList.add(cb.like(root.get("nickName"), user.getNickName() + "%"));
      }
      if (!StringUtils.isBlank(user.getEmail())) {
        predicateList.add(cb.equal(root.get("email"), user.getEmail()));
      }
      if (user.getLocked() != null) {
        predicateList.add(cb.equal(root.get("locked"), user.getLocked()));
      }
      return query.where(predicateList.toArray(new Predicate[0])).getRestriction();
    };
  }
}

package com.alsace.exchange.service.sys.specs;

import com.alsace.exchange.service.sys.domain.User;
import org.springframework.data.jpa.domain.Specification;

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

  /**
   * tel  equal
   */
  public static Specification<User> tel(String tel) {
    return (root, query, builder) -> builder.equal(root.get("tel"), tel);
  }

  /**
   * 登录账号精确
   */
  public static Specification<User> loginAccountEq(String loginAccount) {
    return (root, query, builder) -> builder.equal(root.get("loginAccount"), loginAccount);
  }

  /**
   * 电话精确
   */
  public static Specification<User> telEq(String tel) {
    return (root, query, builder) -> builder.equal(root.get("tel"), tel);
  }

  /**
   * 邮箱精确
   */
  public static Specification<User> emailEq(String email) {
    return (root, query, builder) -> builder.equal(root.get("email"), email);
  }

  /**
   * 锁定精确
   */
  public static Specification<User> lockedEq(Boolean locked) {
    return (root, query, builder) -> builder.equal(root.get("locked"), locked);
  }

  /**
   * 用户名账号
   */
  public static Specification<User> userNameLike(String userName) {
    return (root, query, builder) -> builder.like(root.get("userName"), userName + "%");
  }

  /**
   * 昵称账号
   */
  public static Specification<User> nickNameLike(String nickName) {
    return (root, query, builder) -> builder.like(root.get("nickName"), nickName + "%");
  }

}

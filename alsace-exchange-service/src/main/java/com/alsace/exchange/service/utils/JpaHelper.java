package com.alsace.exchange.service.utils;

import com.alsace.exchange.service.sys.enums.OrderByEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JpaHelper {

  private static final Set<String> excludeProperties = new HashSet<>();

  static {
    excludeProperties.add("userDataList");
    excludeProperties.add("userDataAccount");
  }

  public static <T> Specification<T> buildConditions(T domain, Set<String> likeProperties, Map<String, OrderByEnum> orderProperties) {
    DirectFieldAccessFallbackBeanWrapper beanWrapper = new DirectFieldAccessFallbackBeanWrapper(domain);
    PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
    List<String> propertyNames = new ArrayList<>();
    for (PropertyDescriptor pd : pds) {
      if (!"class".equalsIgnoreCase(pd.getName())) {
        propertyNames.add(pd.getName());
      }
    }

    return (root, query, cb) -> {
      List<Predicate> predicateList = new ArrayList<>();
      propertyNames.forEach(propertyName -> {
        if (excludeProperties.contains(propertyName)) {
          return;
        }
        Object value = beanWrapper.getPropertyValue(propertyName);
        if (ObjectUtils.isEmpty(value)) {
          return;
        }
        if (likeProperties != null && likeProperties.contains(propertyName)) {
          predicateList.add(cb.like(root.get(propertyName), "%" + value + "%"));
        } else {
          predicateList.add(cb.equal(root.get(propertyName), value));
        }
      });

      List<Order> orders = new ArrayList<>();
      if (orderProperties != null) {
        orderProperties.forEach((property, orderBy) -> orders.add(orderBy.equals(OrderByEnum.ASC) ? cb.asc(root.get(property)) : cb.desc(root.get(property))));
      }
      return query.where(predicateList.toArray(new Predicate[0])).orderBy(orders.toArray(new Order[0])).getRestriction();
    };
  }

  public static <T> Specification<T> buildConditions(T domain, Set<String> likeProperties) {
    return buildConditions(domain, likeProperties, null);
  }

  public static <T> Specification<T> buildConditions(T domain) {
    return buildConditions(domain, null, null);
  }

}

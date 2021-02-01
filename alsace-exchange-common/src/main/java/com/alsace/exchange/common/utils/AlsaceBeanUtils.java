package com.alsace.exchange.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class AlsaceBeanUtils extends BeanUtils {

  public static void copyNotNullProperties(Object source, Object dest) {

    BeanUtils.copyProperties(source, dest, getNullPropertyNames(source));

  }

  public static String[] getNullPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for (PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }


  /**
   * 获取类所有属性
   */
  public static List<String> getAllPropertyNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();
    List<String> propertyNames = new ArrayList<>();
    for (PropertyDescriptor pd : pds) {
      if (!"class".equalsIgnoreCase(pd.getName())) {
        propertyNames.add(pd.getName());
      }
    }
    return propertyNames;
  }


}

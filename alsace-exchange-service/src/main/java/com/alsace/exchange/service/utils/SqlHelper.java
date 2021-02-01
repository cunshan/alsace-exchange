package com.alsace.exchange.service.utils;

import com.alsace.exchange.common.utils.AlsaceBeanUtils;
import com.alsace.exchange.service.detection.domain.PersonTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlHelper {

  private static Pattern humpPattern = Pattern.compile("[A-Z]");

  public static String getSelectHeader(Object source, String tableName) {
    if (!StringUtils.isBlank(tableName)) {
      tableName = tableName + ".";
    }
    List<String> properties = AlsaceBeanUtils.getAllPropertyNames(source);
    StringBuilder sb = new StringBuilder();
    sb.append(" select ");
    final String table = tableName;
    properties.forEach(pro -> sb.append(table).append(humpToLine(pro)).append(" as ").append(pro).append(", "));
    return sb.toString();
  }

  private static String humpToLine(String pro) {
    Matcher matcher = humpPattern.matcher(pro);
    StringBuffer sb = new StringBuffer();
    while (matcher.find()) {
      matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
    }
    matcher.appendTail(sb);
    return sb.toString();

  }

  public static void main(String[] args){
    System.out.println(SqlHelper.getSelectHeader(new PersonTask(),"t"));
  }




}

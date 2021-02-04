package com.alsace.exchange.web.config.shiro;

import com.alsace.exchange.service.config.redis.JsonRedisTemplate;
import com.alsace.exchange.web.config.shiro.cache.RedisCacheManager;
import com.alsace.exchange.web.config.shiro.jwt.JwtFilter;
import com.alsace.exchange.web.config.shiro.jwt.JwtRealm;
import com.alsace.exchange.web.config.shiro.jwt.JwtSubjectFactory;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

  @Resource
  private JsonRedisTemplate jsonRedisTemplate;

  @Bean
  public SubjectFactory subjectFactory() {
    // 不使用默认的DefaultSubject创建对象，因为不能创建Session
    return new JwtSubjectFactory();
  }

  @Bean
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinition("/login", "anon,jwt[allowed]");
    chainDefinition.addPathDefinition("/app/send-check/**", "anon,jwt[permissive]");
    chainDefinition.addPathDefinition("/app/login", "anon,jwt[permissive]");
//    chainDefinition.addPathDefinition("/**/downTemplate","anon,jwt[permissive]");

    // SWAGGER2过滤【START】
    chainDefinition.addPathDefinition("/swagger-ui/**", "anon,jwt[permissive]");
    chainDefinition.addPathDefinition("/swagger-ui.html", "anon,jwt[permissive]");
    chainDefinition.addPathDefinition("/swagger-resources", "anon,jwt[permissive]");
    chainDefinition.addPathDefinition("/swagger-resources/**", "anon,jwt[permissive]");
    chainDefinition.addPathDefinition("/webjars/springfox-swagger-ui/**", "anon,jwt[permissive]");
    chainDefinition.addPathDefinition("/v2/api-docs","anon,jwt[permissive]");
    // SWAGGER2过滤【END】

    chainDefinition.addPathDefinition("/**", "jwt");
    return chainDefinition;
  }

  @Bean
  public CacheManager cacheManager(){
    return new RedisCacheManager(jsonRedisTemplate);
  }

  @Bean
  public JwtRealm jwtRealm(){
    JwtRealm jwtRealm = new JwtRealm();
    jwtRealm.setAuthenticationCachingEnabled(true);
    jwtRealm.setAuthorizationCachingEnabled(true);
    jwtRealm.setCachingEnabled(true);
    return jwtRealm;
  }

  @Bean
  public SessionsSecurityManager securityManager(){
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//    securityManager.setSessionManager(sessionManager);
// 关闭 ShiroDAO 功能
    DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
    // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
    defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
    subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
    securityManager.setSubjectDAO(subjectDAO);
    securityManager.setRealm(jwtRealm());
    securityManager.setSubjectFactory(subjectFactory());
    securityManager.setCacheManager(this.cacheManager());
    return securityManager;
  }

  @Bean
  public JwtFilter jwtFilter(){
    return new JwtFilter();
  }

  @Bean
  public List<String> globalFilters(){
    List<String> filters = new ArrayList<>();
    filters.add(DefaultFilter.logout.name());
    filters.add(DefaultFilter.anon.name());
    filters.add(DefaultFilter.invalidRequest.name());
    return filters;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(ShiroFilterChainDefinition shiroFilterChainDefinition) {
    ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

    filterFactoryBean.setLoginUrl("/login");
    filterFactoryBean.setSecurityManager(securityManager());
    filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

    //增加自定义filter
    Map<String, Filter> filterMap = new LinkedHashMap<>();
    filterMap.put("jwt", jwtFilter());
    filterFactoryBean.setFilters(filterMap);

    return filterFactoryBean;
  }

}

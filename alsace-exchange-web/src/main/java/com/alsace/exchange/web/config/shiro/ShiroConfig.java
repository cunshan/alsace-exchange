package com.alsace.exchange.web.config.shiro;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

  private String host = "127.0.0.1:6379";
  private String password = "";

  @Bean
  public SubjectFactory subjectFactory() {
    // 不使用默认的DefaultSubject创建对象，因为不能创建Session
    return new JwtSubjectFactory();
  }

  @Bean
  public ShiroFilterChainDefinition shiroFilterChainDefinition() {
    DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
    chainDefinition.addPathDefinition("/login", "anon");
    chainDefinition.addPathDefinition("/user/save", "jwt");
    return chainDefinition;
  }

  @Bean
  public RedisManager redisManager() {
    RedisManager redisManager = new RedisManager();
    redisManager.setHost(host);
//    redisManager.setPassword(password);
    return redisManager;
  }

  @Bean
  public SessionDAO redisSessionDAO(RedisManager redisManager){
    RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
    redisSessionDAO.setRedisManager(redisManager);
    return redisSessionDAO;
  }

  @Bean
  public SessionManager sessionManager(SessionDAO redisSessionDAO){
    DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
    sessionManager.setSessionDAO(redisSessionDAO);
    return sessionManager;
  }

  @Bean
  public CacheManager cacheManager(RedisManager redisManager){
    RedisCacheManager redisCacheManager = new RedisCacheManager();
    redisCacheManager.setRedisManager(redisManager);

    return redisCacheManager;
  }


  @Bean
  public SessionsSecurityManager securityManager(SessionManager sessionManager, CacheManager cacheManager, JwtRealm jwtRealm){
    DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//    securityManager.setSessionManager(sessionManager);
    securityManager.setCacheManager(cacheManager);
// 关闭 ShiroDAO 功能
    DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
    // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
    defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
    subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
    securityManager.setSubjectDAO(subjectDAO);
    securityManager.setRealm(jwtRealm);
    securityManager.setSubjectFactory(subjectFactory());
    return securityManager;
  }

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(SessionsSecurityManager securityManager,
                                                       ShiroFilterChainDefinition shiroFilterChainDefinition) {
    ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

    filterFactoryBean.setLoginUrl("/login");

    filterFactoryBean.setSecurityManager(securityManager);
    filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

    //增加自定义filter
    Map<String, Filter> filterMap = new LinkedHashMap<>();
    filterMap.put("jwt", new JwtFilter());
    filterFactoryBean.setFilters(filterMap);

    return filterFactoryBean;
  }

}

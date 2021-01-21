package com.alsace.exchange.web.config.cors;

import com.alsace.exchange.web.config.interceptor.OperationLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
        .allowCredentials(true)
        .maxAge(3600)
        .allowedHeaders("*");
  }


  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    List<String> list = new ArrayList<>();
    list.add("/login");
    list.add("/app/send-check/**");
    list.add("/app/login");
    list.add("/person-task/detail/downTemplate");
    list.add("/user/downTemplate");
    list.add("/swagger-ui/**");
    list.add("/swagger-ui.html");
    list.add("/swagger-resources");
    list.add("/swagger-resources/**");
    list.add("/webjars/springfox-swagger-ui/**");
    list.add("/v2/api-docs");
    registry.addInterceptor(new OperationLogInterceptor()).addPathPatterns("/**").excludePathPatterns(list);
  }



}

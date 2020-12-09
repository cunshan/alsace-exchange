package com.alsace.exchange.web.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {

  @Bean
  public Docket docket(){
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())
        .pathMapping("/")
        .groupName("Alsace Exchange");

  }

  /**
   * API 页面上半部分展示信息
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .version("1.0.0")
        .title("Alsace Exchange")
        .description("Alsace框架API文档目录。")
        .termsOfServiceUrl("http://localhost:8080/")
        .license("Apache 2.0")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
        .build();
  }

}

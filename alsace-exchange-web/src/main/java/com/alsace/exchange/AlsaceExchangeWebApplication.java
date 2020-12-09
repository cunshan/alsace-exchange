package com.alsace.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class AlsaceExchangeWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlsaceExchangeWebApplication.class, args);
    }

}

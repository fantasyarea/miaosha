package com.imooc.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


/** war包配置**/
/*
@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
    }
}*/

@SpringBootApplication
public class MainApplication{
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

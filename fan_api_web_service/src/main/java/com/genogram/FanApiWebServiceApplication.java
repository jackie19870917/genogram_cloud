package com.genogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author: yizx
 * @Date: 2018-11-05
 * @Time: 22:23
 * @return:
 * @Description:
 */
@SpringBootApplication
public class FanApiWebServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FanApiWebServiceApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FanApiWebServiceApplication.class);
    }

}

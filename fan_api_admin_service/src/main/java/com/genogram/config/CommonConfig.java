package com.genogram.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.MultipartConfigElement;

/**
 * SwaggerConfig
 *
 * @author Toxicant
 * @date 2016/10/31
 */
@Configuration
public class CommonConfig {
    @Bean
public MultipartConfigElement multipartConfigElement() {
    MultipartConfigFactory factory = new MultipartConfigFactory();
    //文件最大
    factory.setMaxFileSize("10MB"); //KB,MB
    /// 设置总上传数据总大小
    factory.setMaxRequestSize("20MB");
    return factory.createMultipartConfig();
    }

}

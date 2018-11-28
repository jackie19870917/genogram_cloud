package com.genogram.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * commonConfig
 *
 * @author Toxicant
 * @date 2016/10/31
 */
@Configuration
public class CommonConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大50MB
        factory.setMaxFileSize(DataSize.ofBytes(52428800));
        // factory.setMaxFileSize("50MB"); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofBytes(52428800));
        //factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }

}

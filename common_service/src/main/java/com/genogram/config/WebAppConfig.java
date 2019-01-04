package com.genogram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author chicheng
 * @date 2018/8/15
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Value("${tmp.root}")
    private String rootPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pptImg/**").addResourceLocations("file:" + rootPath + "/");
        //registry.addResourceHandler("/jpgImg/**").addResourceLocations("file:E:/Xshell/1/");
        registry.addResourceHandler("/jpgImg/**").addResourceLocations("file:/file_view_temp/Xshell/1/");
        super.addResourceHandlers(registry);
    }
}
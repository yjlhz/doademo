package com.yjlhz.doademo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author lhz
 * @title: UpLoadConfig
 * @projectName doademo
 * @description: 文件上传配置类
 * @date 2022/3/18 17:03
 */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class UpLoadConfig {
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);
        resolver.setMaxInMemorySize(40960);
        //上传文件大小
        resolver.setMaxUploadSize(5 * 1024 * 1024);
        return resolver;
    }
}
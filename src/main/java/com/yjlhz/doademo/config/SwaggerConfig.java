package com.yjlhz.doademo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2   //开启swagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("李华钊")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yjlhz.doademo.controller"))
                .build();
    }

    //配置swagger信息
    private ApiInfo apiInfo(){

        Contact contact = new Contact("李华钊","https://github.com/yjlhz/doademo","2386987985@qq.com");

        return new ApiInfo("李华钊的SwaggerAPI文档",
                "毕设项目api文档",
                "1.0",
                "https://github.com/yjlhz/doademo",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }

}

package com.qin.mq.rabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * description
 *
 * @author DELL
 * @date 2022/01/27 22:47.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                //api基础信息
                .apiInfo(apiInfo())
                //控制开启或关闭swagger
                .enable(true)
                //选择哪些路径和api生成document
                .select()
                //扫描展示api的路径包
                .apis(RequestHandlerSelectors.basePackage("com.qin.mq.rabbitmq"))
                //对所有的路径进行监控
                .paths(PathSelectors.any())
                //构建
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("RabbitMQ Test")
                .description("RabbitMQ Test Samples")
                .version("1.0")
                .build();
    }
}

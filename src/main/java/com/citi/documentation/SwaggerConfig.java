package com.citi.documentation;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swagger(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.citi.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo());
    }

    
    private ApiInfo metaInfo() {
    
     @SuppressWarnings("rawtypes")
	ApiInfo apiInfo = new ApiInfo (
    		 "Simple Spring Boot REST API",
    		 "Desafio Citi Bank. API Restful com Spring Boot",
             "1.0.0",
             "Terms of Service",
                new Contact("Marco Grella", "https://github.com/marcogrella", "marco.grella@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/license.html", new ArrayList<VendorExtension>()
            );
	return apiInfo;
    }

}

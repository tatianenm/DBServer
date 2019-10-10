package com.tatiane;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicates;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	    @Bean
	    public Docket productApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
	                .paths(PathSelectors.any())
	                .paths(Predicates.not(PathSelectors.regex("/error"))) 
	                .build()
	                .useDefaultResponseMessages(false);
	    }

	    private ApiInfo metaInfo() {

	        ApiInfo apiInfo = new ApiInfo(
	                "Restaurante  API",
	                "API Restaurante",
	                "1.0",
	                "Terms of Service",
	                new Contact("Tatiane Mariano", "tatianenmlua@hotmail.com",
	                		"tatianenmlua@hotmail.com"),
	                "Apache License Version 2.0",
	                "https://www.apache.org/licesen.html"
	        );

	        return apiInfo;
	}
}

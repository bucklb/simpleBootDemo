package me.bucklb.simpleBootdemo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

//
// Using stuff from : http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
//
// Try and get auto generation of swagger documentation.
// - after adding _springfox-swagger2_   to pom, can see the raw API stuff via      http://localhost:8090/v2/api-docs (but not nice to read)
//   Seems a bare minimum of info (and includes Spring paths, not just mine)
//
// - after adding _springfox-swagger-ui_ to pom, can get slick swagger display via  http://localhost:8090/swagger-ui.html
//
// https://springfox.github.io/springfox/docs/current/

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    // Some bespoke stuff (to override less helpful defaults settings).  Need to update per project ....
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "bootDemo",
                "API to explore boot for a restFul API (and Swagger)",
                "Draft",
                "Terms of service to come",
                new Contact("bucklb", "https://github.com/bucklb", "bucklb@gmail.com"),
                "License to come", "API license URL", Collections.emptyList());
    }


}

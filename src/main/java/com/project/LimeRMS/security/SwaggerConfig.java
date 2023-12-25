package com.project.LimeRMS.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        String jwt = "AccessToken";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
        Components components = new Components().addSecuritySchemes(
            jwt,
//            new SecurityScheme().name(jwt).type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            new SecurityScheme().name(jwt).type(Type.APIKEY).scheme("bearer").bearerFormat("JWT").in(
                In.HEADER)
        );
        return new OpenAPI()
            .components(components)
            .info(apiInfo())
            .addSecurityItem(securityRequirement);
    }

    private Info apiInfo() {
        return new Info()
            .title("RMS(Rental Management System) prototype")
            .description("사내 대여 자산 관리를 위한 CMS 게시판 만들기 프로젝트")
            .version("0.0.1");
    }
}

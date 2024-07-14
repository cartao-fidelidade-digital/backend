package com.clubeevantagens.authmicroservice.document;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Clubee Backend Document",
        version = "1.0",
        description = "Documentação dos endpoints <br> Obs: Envios usando endpoits com 'Authorization' não estão funcionando no swagger"))
public class SwaggerConfig {

}

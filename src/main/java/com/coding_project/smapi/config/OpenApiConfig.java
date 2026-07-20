package com.coding_project.smapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Global OpenAPI 3.x configuration for the Smart Management API.
 *
 * <p>Defines top-level metadata, server entries, and a JWT Bearer security scheme
 * that is applied globally so every protected endpoint shows the padlock icon in
 * the Swagger UI without repetitive per-operation annotations.</p>
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI smapiOpenAPI() {
        return new OpenAPI()
                .info(buildInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local development server"),
                        new Server()
                                .url("https://api.smapi.example.com")
                                .description("Production server")
                ))
                /*
                 * Register the JWT Bearer security scheme once and apply it
                 * globally so that every endpoint (except /auth/**) is
                 * automatically shown as requiring authentication.
                 */
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description(
                                                "Provide a valid JWT access token obtained from **POST /auth/login**. " +
                                                "Format: `Bearer <token>`"
                                        )
                        )
                );
    }

    private Info buildInfo() {
        return new Info()
                .title("Smart Management API")
                .description(
                        "REST API for the Smart Management platform. " +
                        "Provides full lifecycle management for **Jobs**, **Tasks**, **Inventory**, " +
                        "**Job Materials**, and **File Attachments**. " +
                        "All endpoints except `/auth/**` require a valid JWT Bearer token."
                )
                .version("0.0.1-SNAPSHOT")
                .contact(new Contact()
                        .name("SMAPI Development Team")
                        .email("dev@smapi.example.com")
                        .url("https://github.com/coding-project/smapi")
                );
    }
}

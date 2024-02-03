package com.labsandware.quickpoll.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;


@Configuration
public class OpenAPIConfig {

    @Value("${poll.openapi.dev-url}")
    private  String devUrl;

    @Value("${poll.openapi.prod-url}")
    private  String prodUrl;

    @Bean
    public GroupedOpenApi apiV1() {
        var packagesToScan = new String[] { "com.labsandware.quickpoll.v1.controller" };
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/v1/**")
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public GroupedOpenApi apiV2() {
        var packagesToScan = new String[] { "com.labsandware.quickpoll.v2.controller" };
        return GroupedOpenApi.builder()
                .group("v2")
                .pathsToMatch("/v2/**")
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public GroupedOpenApi apiV3() {
        var packagesToScan = new String[] { "com.labsandware.quickpoll.v3.controller" };
        return GroupedOpenApi.builder()
                .group("v3")
                .pathsToMatch("/v3/**")
                .packagesToScan(packagesToScan)
                .build();
    }

    @Bean
    public OpenAPI myOpenApi() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in production environment");

        Contact contact = new Contact();
        contact.setEmail("dexter@tempmail.com");
        contact.setName("Dexter");
        contact.setUrl("https://www.poll.com");

        License apacheLicense = new License().name("Apache License").url("http://www.apache.org/licenses/LICENSE-2.0");

        Info info =  new Info()
                .title("SpringBoot Complete Tutorial")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to poll tutorials.")
                .termsOfService("https://www.poll.com/terms")
                .license(apacheLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }

}

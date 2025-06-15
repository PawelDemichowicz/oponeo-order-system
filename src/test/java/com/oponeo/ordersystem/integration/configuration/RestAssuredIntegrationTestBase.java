package com.oponeo.ordersystem.integration.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RestAssuredIntegrationTestBase extends AbstractIT {

    @Autowired
    protected ObjectMapper objectMapper;

    public RequestSpecification requestSpecification() {
        return restAssuredBase()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
    }

    private RequestSpecification restAssuredBase() {
        return RestAssured
                .given()
                .config(getConfig())
                .basePath(basePath)
                .port(port);
    }

    private RestAssuredConfig getConfig() {
        return RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((type, s) -> objectMapper));
    }
}

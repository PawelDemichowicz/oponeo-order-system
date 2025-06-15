package com.oponeo.ordersystem.integration.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@TestConfiguration
public class PersistenceContainerTestConfiguration {

    private static final String POSTGRESQL_USERNAME = "test";
    private static final String POSTGRESQL_PASSWORD = "test";
    private static final String POSTGRESQL_BEAN_NAME = "postgresql";
    private static final String POSTGRESQL_CONTAINER = "postgres:17.2";

    @Bean
    @Qualifier(POSTGRESQL_BEAN_NAME)
    PostgreSQLContainer<?> postgresqlContainer() {
        PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRESQL_CONTAINER)
                .withUsername(POSTGRESQL_USERNAME)
                .withPassword(POSTGRESQL_PASSWORD);
        postgreSQLContainer.start();
        return postgreSQLContainer;
    }

    @Bean
    DataSource dataSource(final PostgreSQLContainer<?> container) {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName(container.getDriverClassName())
                .url(container.getJdbcUrl())
                .username(container.getUsername())
                .password(container.getPassword())
                .build();
    }
}
